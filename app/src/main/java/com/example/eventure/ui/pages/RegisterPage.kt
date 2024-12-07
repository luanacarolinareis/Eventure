package com.example.eventure.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.draw.clip
import com.example.eventure.R
import com.example.eventure.components.bebasNeueFont
import androidx.compose.ui.unit.sp
import com.example.eventure.components.PasswordField
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterPage( onRegister: (String, String, String) -> Unit = { _, _, _ -> } ) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("User") } // Default to "User"

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance() // Initialize FirebaseAuth
    val context = LocalContext.current // Obtains the current context

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCE2B1))
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(250.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Register for Eventure!",
                style = TextStyle(
                    fontFamily = bebasNeueFont,
                    fontSize = 25.sp
                ),
                color = Color(0xFF095FA7),
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF0CCA9D),
                    unfocusedBorderColor = Color.White
                )
            )

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF0CCA9D),
                    unfocusedBorderColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password Field
            PasswordField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                passwordVisible = passwordVisible,
                onPasswordToggleClick = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Confirm Password Field
            PasswordField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                passwordVisible = confirmPasswordVisible,
                onPasswordToggleClick = { confirmPasswordVisible = !confirmPasswordVisible }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // User Type Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                RadioButton(
                    selected = userType == "Organizer",
                    onClick = { userType = "Organizer" }
                )
                Text("I'm an Organizer")

                RadioButton(
                    selected = userType == "User",
                    onClick = { userType = "User" }
                )
                Text("I'm a User")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Verifies if the name is empty
                    if (name.isBlank()) {
                        Toast.makeText(
                            context,
                            "Name cannot be empty!",
                            Toast.LENGTH_LONG
                        ).show()
                        return@Button
                    }

                    // Passwords must match
                    if (password == confirmPassword) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val uid = auth.currentUser?.uid ?: ""
                                    val db = FirebaseFirestore.getInstance()

                                    // Creation of user data with name, email, type and uid
                                    val userData = mapOf(
                                        "name" to name,
                                        "email" to email,
                                        "type" to userType,
                                        "uid" to uid
                                    )

                                    // Saves user data to Firestore
                                    db.collection("users").document(uid).set(userData)
                                        .addOnSuccessListener {
                                            Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG).show()
                                            onRegister(email, password, userType)
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context, "Failed to save user data!", Toast.LENGTH_LONG).show()
                                        }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Registration failed: ${task.exception?.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        // Otherwise...
                    } else {
                        Toast.makeText(
                            context,
                            "Passwords do not match",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0CCA9D)
                )
            ) {
                Text(text = "Register", color = Color.White)
            }
        }
    }
}
