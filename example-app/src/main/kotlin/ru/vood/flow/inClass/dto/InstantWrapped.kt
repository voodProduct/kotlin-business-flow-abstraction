package ru.vood.flow.inClass.dto

import java.time.Instant

data class InstantWrapped(
    val instant: Instant,
) : IInputDto
