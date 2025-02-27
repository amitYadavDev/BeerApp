package app.exam.beerapp.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import app.exam.beerapp.presentation.viewmodel.BeerViewModel

@Composable
fun BeerScreen(viewModel: BeerViewModel) {
    val context = LocalContext.current
    val beersData = viewModel.beerPagingFlow.collectAsLazyPagingItems()

    LaunchedEffect(key1 = beersData.loadState) {
        if (beersData.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (beersData.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if(beersData.loadState.refresh is LoadState.Loading) {
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center)
//            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                 items(beersData) {beer->
                     if(beer != null) {
                         BeerItem(beer)
                     }
                 }
                item {
                    if(beersData.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}