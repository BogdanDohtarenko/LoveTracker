package com.ideasapp.lovetracker.presentation.elements


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ideasapp.lovetracker.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.ideasapp.lovetracker.domain.entity.NotificationData

@Composable
fun NotificationDialog(
    onDismissRequest: () -> Unit,
    onSendClick: (NotificationData) -> Unit,
) {
    var title by remember { mutableStateOf("It's your partner") }
    var body by remember { mutableStateOf("miss you") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column {
                TextField(value = title, onValueChange = { title = it }, label = { Text("title:") })
                TextField(value = body, onValueChange = { body = it }, label = { Text("body:") })

                Button(
                    onClick = {
                        val notificationData = NotificationData(title, body)
                        onSendClick(notificationData)
                    },
                ) {
                    Text(text = stringResource(id = R.string.send_notification))
                }
            }
        }
    }
}