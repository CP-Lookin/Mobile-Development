package com.example.lookinapp.screen

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.example.lookinapp.R
import com.example.lookinapp.ResultActivity
import com.example.lookinapp.api.ApiConfig
import com.example.lookinapp.api.PredictionResponse
import com.example.lookinapp.screen.pref.PreferencesHelper
import com.example.lookinapp.screen.pref.compressImage
import com.example.lookinapp.ui.theme.BlueButton
import com.example.lookinapp.ui.theme.GrayCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    var isRefreshing by remember { mutableStateOf(false) }
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var bottomSheetVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) } // Loading state
    val photoUri = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val isDarkMode = remember { PreferencesHelper.getDarkModeState(context) }
    val backgroundColor = if (isDarkMode) Color(0xFF474444) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSuccess ->
            if (isSuccess && photoUri.value != null) {
                selectedImageUri = photoUri.value
                Toast.makeText(context, "Image captured successfully", Toast.LENGTH_SHORT).show()
            } else {
                selectedImageUri = null
                Toast.makeText(context, "Failed to capture image", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val uri = createImageUri(context)
                photoUri.value = uri
                if (uri != null) {
                    cameraLauncher.launch(uri)
                } else {
                    Toast.makeText(context, "Failed to create image URI", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val showImagePickerDialog = { bottomSheetVisible = true }

    val onGalleryClick = {
        imagePickerLauncher.launch("image/*")
        bottomSheetVisible = false
    }

    val onCameraClick = {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            val uri = createImageUri(context)
            photoUri.value = uri
            if (uri != null) {
                cameraLauncher.launch(uri)
            } else {
                Toast.makeText(context, "Failed to create image URI", Toast.LENGTH_SHORT).show()
            }
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        bottomSheetVisible = false
    }

    fun getPredictionFromImage(uri: Uri, context: Context) {
        isLoading = true

        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            inputStream?.let {
                val byteArray = it.readBytes()
                val tempFile = File(context.cacheDir, "temp_image.jpg")
                tempFile.writeBytes(byteArray)
                val compressedFile = compressImage(tempFile)

                val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), compressedFile)
                val body = MultipartBody.Part.createFormData("image", compressedFile.name, requestFile)

                ApiConfig.apiService.getPrediction(body).enqueue(object : Callback<PredictionResponse> {
                    override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                        isLoading = false

                        if (response.isSuccessful) {
                            val predictionResponse = response.body()
                            if (predictionResponse != null && predictionResponse.status == "success") {
                                val faceShape = predictionResponse.data.faceShapeResult
                                val gender = predictionResponse.data.genderResult

                                val intent = Intent(context, ResultActivity::class.java).apply {
                                    putExtra("selectedImageUri", uri.toString())
                                    putExtra("faceShape", faceShape)
                                    putExtra("gender", gender)
                                }
                                context.startActivity(intent)
                            }
                        } else {
                            Toast.makeText(context, "API Error: ${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                        isLoading = false // Stop loading
                        Toast.makeText(context, "Failed to get prediction: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } ?: run {
                Toast.makeText(context, "Failed to open input stream for the image", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error accessing the file", Toast.LENGTH_SHORT).show()
            isLoading = false
        }
    }

    val navigateToResultActivity = {
        selectedImageUri?.let {
            getPredictionFromImage(it, context)
        } ?: run {
            Toast.makeText(context, "Please select an image first", Toast.LENGTH_SHORT).show()
        }
    }

    val onRefresh = {
        isRefreshing = true
        selectedImageUri = null
        isRefreshing = false
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { onRefresh() }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
            ) {
                Text(
                    text = "Check Face Shape",
                    style = MaterialTheme.typography.headlineMedium,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp, max = 350.dp)
                            .clickable { showImagePickerDialog() },
                        shape = MaterialTheme.shapes.medium.copy(CornerSize(16.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(GrayCard),
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedImageUri != null) {
                                Image(
                                    painter = rememberImagePainter(selectedImageUri),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.vector),
                                    contentDescription = "Select Image",
                                    modifier = Modifier.size(100.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isLoading) {
                        LoadingAnimation()
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navigateToResultActivity() },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BlueButton,
                            contentColor = Color.White
                        ),
                        enabled = !isLoading // Disable button while loading
                    ) {
                        Text(
                            text = "Scan My Face",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 18.sp
                        )
                    }
                }
            }

            if (bottomSheetVisible) {
                ModalBottomSheet(
                    onDismissRequest = { bottomSheetVisible = false },
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.systemBars)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "Choose an Option",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 5.dp),
                            color = Color.Black
                        )
                        TextButton(onClick = onGalleryClick) {
                            Text("Select from Gallery", color = Color.Black)
                        }
                        TextButton(onClick = onCameraClick) {
                            Text("Take a Photo", color = Color.Black, modifier = Modifier.padding(bottom = 30.dp))
                        }
                    }
                }
            }
        }
    }
}

fun createImageUri(context: Context): Uri? {
    val contentResolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "captured_image_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Camera")
    }
    return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}
