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
import com.example.eventure.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.eventure.components.PasswordField
import com.example.eventure.components.uniSans
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

@Composable
@Preview(showBackground = true)
fun LoginPage(onLogin: (String, String) -> Unit = { _, _ -> },
              onGoToRegister: () -> Unit = {},
              onContinueAsGuest: () -> Unit = {}) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance() // Firebase Authentication instance
    val context = LocalContext.current // To show toast messages

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
                text = "Maps for events!",
                style = TextStyle(
                    fontFamily = uniSans,
                    fontSize = 25.sp
                ),
                color = Color(0xFF095FA7),
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Login
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

            PasswordField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                passwordVisible = passwordVisible,
                onPasswordToggleClick = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Check that the email and password are not empty before trying to login
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        // Authenticates user with Firebase
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Login successful
                                    Toast.makeText(context, "Login successful!", Toast.LENGTH_LONG).show()
                                    onLogin(email, password) // Can redirect or save data
                                } else {
                                    // Login failed
                                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        // Shows an error message if fields are empty
                        Toast.makeText(context, "Please fill in both email and password", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0CCA9D)
                )
            )  {
                Text(text = "Login", color = Color.White)
            }

            Button(
                onClick = { onContinueAsGuest() },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0CCA9D)
                )
            ) {
                Text(text = "Continue as Guest", color = Color.White)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Register
            val annotatedString = buildAnnotatedString {
                append("Not registered yet? ")
                pushStyle(
                    SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xFF095FA7),
                        fontWeight = FontWeight.Bold
                    )
                )
                append("Register.")
                pop()
            }

            ClickableText(
                text = annotatedString,
                onClick = { onGoToRegister() },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .wrapContentSize(Alignment.Center)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Forgot password
            val forgotPasswordText = buildAnnotatedString {
                pushStyle(
                    SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xFF095FA7),
                        fontWeight = FontWeight.Bold
                    )
                )
                append("Reset password.")
                pop()
            }

            ClickableText(
                text = forgotPasswordText,
                onClick = {
                    if (email.isNotEmpty()) {
                        auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Password reset email sent!", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, "Failed to send reset email: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        Toast.makeText(context, "Please enter your email address first.", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}
