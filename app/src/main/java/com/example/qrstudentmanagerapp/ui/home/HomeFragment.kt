package com.example.qrstudentmanagerapp.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.qrstudentmanagerapp.R
import com.example.qrstudentmanagerapp.databinding.FragmentHomeBinding
import com.example.qrstudentmanagerapp.ui.QR.GennQR

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Referencia al ImageView donde se mostrará el QR
        val qrCodeImageView: ImageView = root.findViewById(R.id.qrCodeImageView)

        // Instancia de GennQR
        val gennQR = GennQR()

        // Generar el código QR
        gennQR.generateQRCode("https://www.ejemplo.com", 200, object : GennQR.QRCodeCallback {
            override fun onSuccess(qrCodeBitmap: Bitmap) {
                // Mostrar el código QR en el ImageView
                activity?.runOnUiThread {
                    qrCodeImageView.setImageBitmap(qrCodeBitmap)
                }
            }

            override fun onError(errorMessage: String) {
                // Mostrar un mensaje de error
                activity?.runOnUiThread {
                    qrCodeImageView.setImageResource(android.R.drawable.ic_dialog_alert)
                }
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}