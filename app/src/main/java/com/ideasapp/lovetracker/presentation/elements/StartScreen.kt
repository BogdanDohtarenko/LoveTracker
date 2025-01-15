package com.ideasapp.lovetracker.presentation.elements

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.lovetracker.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.ideasapp.lovetracker.domain.entity.NotificationData

@Composable
fun StartScreen(
    onSendClick: (NotificationData) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(20.dp)) {
                Text("Sex",
                    fontSize = 26.sp)
            }
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(20.dp)) {
                Text("Sleepover",
                    fontSize = 26.sp)
            }
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(20.dp)) {
                Text("Topics",
                    fontSize = 26.sp)
            }
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(20.dp),) {
                Text("Walks",
                    fontSize = 26.sp)
            }
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .padding(top = 70.dp),
                onClick = { showDialog = true }, shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(red = 158, green = 70, blue = 90))) {
                Image(painter = painterResource(id = R.drawable.heart),
                    contentDescription = stringResource(id = R.string.heart_descr))
            }
        }
    }
    if (showDialog) {
        NotificationDialog(
            onDismissRequest = { showDialog = false },
            onSendClick = onSendClick
        )
    }

}