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
import com.example.eventure.components.bebasNeueFont
import com.example.eventure.components.PasswordField
import com.example.eventure.components.uniSans

@Composable
@Preview(showBackground = true)
fun LoginPage(onLogin: (String, String) -> Unit = { _, _ -> },
              onGoToRegister: () -> Unit = {},
              onContinueAsGuest: () -> Unit = {}) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

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
                label = { Text("Email")},
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
                onClick = { onLogin(email, password) },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0CCA9D)
                )
            ) {
                Text(text = "Login", color = Color.White, fontFamily = uniSans)
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
                        fontFamily = uniSans,
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
        }
    }
}
