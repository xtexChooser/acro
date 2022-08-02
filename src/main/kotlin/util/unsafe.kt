package acro.util

import sun.misc.Unsafe

val theUnsafe = Unsafe::class.java.getDeclaredField("theUnsafe").run {
    isAccessible = true
    get(null) as Unsafe
}
