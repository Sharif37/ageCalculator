package com.example.agecalculator

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agecalculator.ui.theme.AgeCalculatorTheme
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgeCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AgeCalculator("calculate your ")

                }
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun AgeCalculator(msg:String) {
    var ageInMinutes by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =Arrangement.Top


    ) {
        Text(text = msg.uppercase(),
            fontSize = 30.sp,
            color = Color(125,0,0),
            modifier=Modifier.
            padding(10.dp)
        )
        Text(text ="age".uppercase(),
            fontSize = 30.sp,
            modifier=Modifier.background(Color.Cyan),
            color= Color.White

        )
        Text(text ="in minutes".uppercase(),
            fontSize = 30.sp,
            color = Color(125,0,0)
        )

        /*OutlinedTextField(
            value = age.toString(),
            onValueChange = { newAge ->
                age = newAge.toIntOrNull() ?: 0
            },
            label = { Text("Enter your age") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )*/


        Spacer(modifier = Modifier.height(16.dp))
        val selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
        val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
        val selectedDateText = remember { mutableStateOf("Select Date: ") }

        val context = LocalContext.current
        fun datePickerShow(){
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    selectedDate.set(year, month, dayOfMonth)
                    selectedDateText.value = "Select Date: ${dateFormat.format(selectedDate.time)}"
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        fun calculateInMin(selectDate: Calendar):Int{
            val today=Calendar.getInstance()
            /* Log.d("date",today.toString())*/

            var year=today.get(Calendar.YEAR)-selectDate.get(Calendar.YEAR)
            var month=today.get(Calendar.MONTH)-selectDate.get(Calendar.MONTH)
            val day=today.get(Calendar.DAY_OF_MONTH)-selectDate.get(Calendar.DAY_OF_MONTH)


            if (month < 0 ) {
                year-= 1
            }
            Log.d("date format","$year $month $day ")
            return year*365*1440 + month*30*1440 + day*1440

        }



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerShow() }
                .background(MaterialTheme.colorScheme.background)
                .border(
                    width = 0.dp,
                    color = MaterialTheme.colorScheme.primary,
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)

        ) {
            Text(
                text = selectedDateText.value,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                ageInMinutes=calculateInMin(selectedDate)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = null)
            Spacer(modifier = Modifier.width(10.dp))
            Text("Calculate")
        }
        Spacer(modifier = Modifier.height(10.dp))





        TextField(
            value = "Age in minutes: $ageInMinutes",
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            readOnly = true
        )



    }
}


@Preview(showBackground = true)
@Composable
fun AgeCalculatorPreview() {
    AgeCalculator("calculate your")

}