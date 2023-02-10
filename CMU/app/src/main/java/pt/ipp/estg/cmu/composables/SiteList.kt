package pt.ipp.estg.cmu.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.ipp.estg.cmu.classes.Site

@Composable
fun SiteList(sites: ArrayList<Site>) {
    val paddingModifier = Modifier.padding(5.dp)
    LazyColumn {
        items(sites.size) { index ->
            Card(
                elevation = CardDefaults.cardElevation(10.dp),
                border = BorderStroke(1.dp, Color.Blue),
                modifier = paddingModifier.fillMaxWidth(),
            ) {
                Column(modifier = paddingModifier) {
                    Row {
                        sites[index].addressInfo.Title?.let {
                            Text(text = it, modifier = Modifier.padding(3.dp))
                        }
                    }
                    Row {
                        sites[index].addressInfo.AddressLine1?.let {
                            Text(text = it, modifier = Modifier.padding(3.dp))
                        }
                    }
                    Row {
                        sites[index].addressInfo.AddressLine2?.let {
                            Text(text = it, modifier = Modifier.padding(3.dp))
                        }
                    }
                    Row {
                        sites[index].status.Title?.let {
                            Text(text = it, modifier = Modifier.padding(3.dp))
                        }
                    }
                }
            }
        }
    }
}