package com.tbi.supplierplus.framework.ui.qr

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.tbi.supplierplus.business.utils.showSnackbar
import com.tbi.supplierplus.databinding.FragmentQRCodeScannerBinding
import com.tbi.supplierplus.framework.ui.purchase.PurchaseViewModel
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class QRCodeScannerFragment : Fragment() {

    private lateinit var binding: FragmentQRCodeScannerBinding
    private val salesViewModel: SalesViewModel by activityViewModels()
    private val purchaseViewModel: PurchaseViewModel by activityViewModels()

    private lateinit var codeScanner: CodeScanner
    private var isPermissionGranted by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQRCodeScannerBinding.inflate(inflater)
        binding.viewModel = salesViewModel
        binding.lifecycleOwner = this

        val choice = requireArguments().getString("choice")

        isPermissionGranted = isCameraPermissionGranted()

        codeScanner = CodeScanner(requireContext(), binding.codeScanner)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
      //  codeScanner.decodeCallback = DecodeCallback {
//
      //      lifecycleScope.launch {
      //          if (choice.equals("customer")) {
      //              salesViewModel.setCustomerID(it.text)
      //              salesViewModel.navigateToCustomersProfile.value = true
      //          }
      //          if (choice.equals("item"))
      //              salesViewModel.setCurrentItemID(it.text)
//
      //       //   if (choice.equals("purchase_item"))
      //       //    //   purchaseViewModel.setCurrentItem(it.text)
      //       //   findNavController().popBackStack()
////
      //      }
      //  }

        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS

            showSnackbar(activity!!, "Camera initialization error: ${it.message}")
        }

        binding.codeScanner.setOnClickListener {
//            codeScanner.startPreview()
        }
        codeScanner.startPreview()

        return binding.root
    }

    private fun isCameraPermissionGranted(): Boolean {

        return if (ActivityCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(
                    android.Manifest.permission.CAMERA,
                ),
                1000

            )

            false
        } else {
            true
        }
    }


}