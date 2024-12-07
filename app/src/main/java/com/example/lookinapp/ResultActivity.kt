package com.example.lookinapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.lookinapp.api.data.getHairStyleData
import com.example.lookinapp.screen.pref.PreferencesHelper
import com.example.lookinapp.ui.theme.BlueButton
import com.example.lookinapp.ui.theme.LookinAppTheme

class ResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageUriString = intent.getStringExtra("selectedImageUri")
        val faceShape = intent.getStringExtra("faceShape")
        val gender = intent.getStringExtra("gender")


        val imageUri = Uri.parse(imageUriString)

        setContent {
            LookinAppTheme {
                ShowImage(imageUri, faceShape, gender)
            }
        }
    }
}

//Function Untuk Show Image Result
@Composable
fun ShowImage(imageUri: Uri?, faceShape: String?, gender: String?) {

    // Variable Context
    val context = LocalContext.current

    // Variable Untuk Theme
    val isDarkMode = remember { PreferencesHelper.getDarkModeState(context) }
    val textColor = if (isDarkMode) Color.White else Color(0xFF474444)

    imageUri?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = imageUri,
                    ),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .offset(y = (150).dp)
            ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$faceShape",
                    color = textColor,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
            }

            // Gender Layout
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (70).dp)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "$gender",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )

            }

            // Hasil Analyst Gambar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (150).dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {

                        // Recomendasi Oval HairStyle
                        if (faceShape == "Oval" && (gender == "Female" || gender == "Male")) {
                            // Proses untuk Oval face shape
                            val hairStyleDataList = getHairStyleData(context)
                            val filteredData = hairStyleDataList?.firstOrNull {
                                it.faceShape.equals(faceShape, ignoreCase = true) && it.gender.equals(gender, ignoreCase = true)
                            }
                            filteredData?.let { hairStyleData ->
                                val images = hairStyleData.images.values.toList()
                                val text = hairStyleData.nameHairStyle.values.toList()

                                val intent = Intent(context, HairStyleActivity::class.java)
                                intent.putStringArrayListExtra("imagesList", ArrayList(images))
                                intent.putStringArrayListExtra("textList", ArrayList(text))
                                intent.putExtra("faceShape", hairStyleData.faceShape)
                                context.startActivity(intent)
                            }
                        }

                        // Recomendasi Round HairStyle
                        else if (faceShape == "Round" && (gender == "Female" || gender == "Male")) {
                            // Proses untuk Round face shape
                            val hairStyleDataList = getHairStyleData(context)
                            val filteredData = hairStyleDataList?.firstOrNull {
                                it.faceShape.equals(faceShape, ignoreCase = true) && it.gender.equals(gender, ignoreCase = true)
                            }
                            filteredData?.let { hairStyleData ->
                                val images = hairStyleData.images.values.toList()
                                val text = hairStyleData.nameHairStyle.values.toList()

                                val intent = Intent(context, HairStyleActivity::class.java)
                                intent.putStringArrayListExtra("imagesList", ArrayList(images))
                                intent.putStringArrayListExtra("textList", ArrayList(text))
                                intent.putExtra("faceShape", hairStyleData.faceShape)
                                context.startActivity(intent)
                            }
                        }

                        // Recomendasi Squaare HairStyle
                        else if (faceShape == "Square" && (gender == "Female" || gender == "Male")) {
                            // Proses untuk Shape face shape
                            val hairStyleDataList = getHairStyleData(context)
                            val filteredData = hairStyleDataList?.firstOrNull {
                                it.faceShape.equals(faceShape, ignoreCase = true) && it.gender.equals(gender, ignoreCase = true)
                            }
                            filteredData?.let { hairStyleData ->
                                val images = hairStyleData.images.values.toList()
                                val text = hairStyleData.nameHairStyle.values.toList()

                                val intent = Intent(context, HairStyleActivity::class.java)
                                intent.putStringArrayListExtra("imagesList", ArrayList(images))
                                intent.putStringArrayListExtra("textList", ArrayList(text))
                                intent.putExtra("faceShape", hairStyleData.faceShape)
                                context.startActivity(intent)
                            }
                        }

                    },
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                    modifier = Modifier.width(250.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor  = BlueButton,
                        contentColor = Color.White
                    )
                ) {
                    Text("Check Hairsyle",  modifier = Modifier.padding(5.dp),
                        fontSize = 18.sp    )
                }
            }
}

            // Header Floating Page Result
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 32.dp)
            ) {
                Text(
                    text = "Result",
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                )
            }

            FloatingActionButton(
                onClick = {
                    (context as? ComponentActivity)?.finish()
                },
                containerColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(30.dp),
                        tint = textColor
                    )
                }
            }

        }
    } ?: run {
        Text(text = "No image selected", style = MaterialTheme.typography.bodyLarge)
    }
}
