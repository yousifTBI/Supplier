package com.tbi.supplierplus.framework.ui.sales.execute_customer

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.business.utils.showSnackbar
import com.tbi.supplierplus.databinding.FragmentExecuteCustomerBinding
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.framework.utils.PrintPic
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


@AndroidEntryPoint
class ExecuteCustomerFragment : Fragment() {
    private lateinit var binding: FragmentExecuteCustomerBinding
    private val viewModel: SalesViewModel by activityViewModels()
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mmSocket: BluetoothSocket? = null
    var mmDevice: BluetoothDevice? = null

    var mmOutputStream: OutputStream? = null
    var mmInputStream: InputStream? = null
    var workerThread: Thread? = null

    lateinit var readBuffer: ByteArray
    var readBufferPosition = 0
    var counter = 0


    @Volatile
    var stopWorker = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExecuteCustomerBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.cashEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s!!.isNotEmpty()) {
                //    binding.deferredEditText.setText(
                      // viewModel.calculateNewDeferred(
                      //     s.toString().toFloat()
                      // ).toString()
                 //   )
                }
            }
        })

    //   viewModel.requiredAmount.observe(viewLifecycleOwner) {
    //       binding.deferredEditText.setText(
    //           viewModel.calculateNewDeferred(viewModel.collection.value!!.toFloat()).toString()
    //       )
    //   }

    //   viewModel.appliedDiscount.observe(viewLifecycleOwner) {
    //       if (it.isNotEmpty()) viewModel.executeRequiredAmount(binding.percentageCheckBox.isChecked)
    //   }
    //   binding.percentageCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
    //       viewModel.executeRequiredAmount(isChecked)
    //   }
    //   viewModel.message.observe(viewLifecycleOwner) {
    //       if (it != null) {
    //           showSnackbar(activity!!, it)
    //           viewModel.onDoneMsg()
    //           findNavController().popBackStack()
    //       }
    //   }

    //   viewModel.print.observe(viewLifecycleOwner) {
    //       if (it) {
    //           print()
    //           viewModel.print.value = false
    //       }
    //   }
        return binding.root
    }

    fun print() {
        findBT()
        openBT()

        sendData()

    }

    fun findBT() {
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (mBluetoothAdapter == null) {
                viewModel.message.value = "No bluetooth adapter available"
            }
            if (mBluetoothAdapter?.isEnabled == false) {
                val enableBluetooth = Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE
                )
                startActivityForResult(enableBluetooth, 0)
            }
            val pairedDevices: Set<BluetoothDevice> = mBluetoothAdapter?.bondedDevices!!
            if (pairedDevices.isNotEmpty()) {
                for (device in pairedDevices) {

                    // MP300 is the name of the bluetooth printer device
                    if (device.name == "InnerPrinter") {
                        mmDevice = device
                        break
                    }
                }
            }
            viewModel.message.value = "Bluetooth Device Found"
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Tries to open a connection to the bluetooth printer device
    @Throws(IOException::class)
    fun openBT() {
        try {
            // Standard SerialPortService ID
            val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
            mmSocket = mmDevice!!.createRfcommSocketToServiceRecord(uuid)
            mmSocket?.connect()
            mmOutputStream = mmSocket?.outputStream
            mmInputStream = mmSocket?.inputStream
            beginListenForData()
            viewModel.message.value = "Bluetooth Opened"
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // After opening a connection to bluetooth printer device,
    // we have to listen and check if a data were sent to be printed.
    fun beginListenForData() {
        try {
            val handler = Handler()

            // This is the ASCII code for a newline character
            val delimiter: Byte = 10
            stopWorker = false
            readBufferPosition = 0
            readBuffer = ByteArray(1024)
            workerThread = Thread {
                while (!Thread.currentThread().isInterrupted
                    && !stopWorker
                ) {
                    try {
                        val bytesAvailable: Int = mmInputStream!!.available()
                        if (bytesAvailable > 0) {
                            val packetBytes = ByteArray(bytesAvailable)
                            mmInputStream?.read(packetBytes)
                            for (i in 0 until bytesAvailable) {
                                val b = packetBytes[i]
                                if (b == delimiter) {
                                    val encodedBytes = ByteArray(readBufferPosition)
                                    System.arraycopy(
                                        readBuffer, 0,
                                        encodedBytes, 0,
                                        encodedBytes.size
                                    )
                                    val data = String(
                                        encodedBytes
                                    )
                                    readBufferPosition = 0
                                } else {
                                    readBuffer[readBufferPosition.inc()] = b
                                }
                            }
                        }
                    } catch (ex: IOException) {
                        stopWorker = true
                    }
                }
            }
            workerThread?.start()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
 * This will send data to be printed by the bluetooth printer
 */
    @Throws(IOException::class)
    fun sendData() {
        try {

            // the text typed by the user

            var printPic = PrintPic.getInstance()
        //   printPic.init(
        //    //  viewModel.textAsBitmap(
        //    //      context = context!!,
        //    //      textSize = 18,
        //    //      textWidth = 370
        //    //  )
        //   )

            var draw = printPic.printDraw()

            mmOutputStream!!.write(draw)
            // tell the user data were sent
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Log.e("PrintError1", e.message.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("PrintErro2r", e.message.toString())

        }
        closeBT()
    }

    // Close the connection to bluetooth printer.
    @Throws(IOException::class)
    fun closeBT() {
        try {
            stopWorker = true
            mmOutputStream?.close()
            mmInputStream?.close()
            mmSocket?.close()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}