package com.pmprogramms.contacts.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.pmprogramms.contacts.databinding.DialogSourceBinding

class OpenSourcesDialog : DialogFragment() {
    private val source =
        "<b> Android Image Cropper <b/> <br/> " +
                "source: https://github.com/ArthurHub/Android-Image-Cropper <br/>" +
                "Licence: Originally forked from edmodo/cropper.\n" +
                "\n" +
                "Copyright 2016, Arthur Teplitzki, 2013, Edmodo, Inc.\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License."

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogSourceBinding.inflate(LayoutInflater.from(context))
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        builder.setTitle("Open sources")
        binding.text.text = Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT)

        builder.setPositiveButton(
            "Ok"
        ) { _, _ ->
            //nothing
        }
        return builder.create()
    }

}