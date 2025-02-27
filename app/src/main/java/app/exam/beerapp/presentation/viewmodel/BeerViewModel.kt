package app.exam.beerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import app.exam.beerapp.data.local.BeerEntity
import app.exam.beerapp.data.mappers.toBeerDomainData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    pager: Pager<Int, BeerEntity>
) : ViewModel() {
    val beerPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toBeerDomainData() }
        }
        .cachedIn(viewModelScope)
}