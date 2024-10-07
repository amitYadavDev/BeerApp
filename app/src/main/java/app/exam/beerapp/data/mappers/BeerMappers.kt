package app.exam.beerapp.data.mappers

import app.exam.beerapp.data.local.BeerDao
import app.exam.beerapp.data.local.BeerEntity
import app.exam.beerapp.data.remote.BeerDto
import app.exam.beerapp.domain.BeerDomainData


fun BeerDto.toBeerEntity(): BeerEntity {
    return BeerEntity(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = first_brewed,
        imageUrl = image_url
    )
}

fun BeerEntity.toBeerDomainData(): BeerDomainData {
    return BeerDomainData(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = firstBrewed,
        imageUrl = imageUrl
    )
}