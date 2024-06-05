package com.vunlph30245.roomdb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.vunlph30245.roomdb.ui.theme.RoomDBTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomDBTheme {
                Scaffold(modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(16.dp)) { innerPadding ->
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen () {
    val context = LocalContext.current

    val db = Room.databaseBuilder(
        context,
        StudentDB::class.java, "student-db"
    ).allowMainThreadQueries().build()

    var listStudents by remember {
        mutableStateOf(db.studentDAO().getAll())
    }

    Column (Modifier.fillMaxWidth()){
        Text(
            text = "Quan ly Sinh vien",
            style = MaterialTheme.typography.titleLarge
        )

        Button(onClick = {
            db.studentDAO().insert(StudentModel(hoten = "Nguyen Long Vu", mssv = "PH30245", diemTB = 9.5f))
            db.studentDAO().insert(StudentModel(hoten = "Nguyen Long A", mssv = "PH30246", diemTB = 9.3f))
            db.studentDAO().insert(StudentModel(hoten = "Nguyen Long B", mssv = "PH30247", diemTB = 9.1f))
            listStudents = db.studentDAO().getAll()

        }) {
            Text(text = "Thêm SV")
        }

        LazyColumn {

            items(listStudents) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ){
                    Text(modifier = Modifier.weight(1f), text = it.uid.toString())
                    Text(modifier = Modifier.weight(1f), text = it.hoten.toString())
                    Text(modifier = Modifier.weight(1f), text = it.mssv.toString())
                    Text(modifier = Modifier.weight(1f), text = it.diemTB.toString())
                }
                Divider()
            }
        }
    }
}