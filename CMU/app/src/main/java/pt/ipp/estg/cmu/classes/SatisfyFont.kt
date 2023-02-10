package pt.ipp.estg.cmu.classes

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import pt.ipp.estg.cmu.R

@OptIn(ExperimentalTextApi::class)
class SatisfyFont {
    private val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    private val fontName = GoogleFont("Satisfy")

    val titleFamily = FontFamily(
        Font(googleFont = fontName, fontProvider = provider)
    )

}