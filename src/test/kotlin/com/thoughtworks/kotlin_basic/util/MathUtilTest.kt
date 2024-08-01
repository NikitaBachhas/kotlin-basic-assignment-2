package com.thoughtworks.kotlin_basic.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MathUtilTest {
    @Test
    fun `1 + 2 = 3`() {
        val mathUtil = MathUtil()
        assertEquals(3, mathUtil.add(1, 2))
    }
}