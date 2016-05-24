package com.erindavide.listaspesa.utilities

/**
 * Created by demiurgo on 5/24/16.
 */


/**
 * Clamps the value of a float between min and max
 *
 * @param min defaults to 0f
 * @param max defaults to 1f
 *
**/
fun Float.clamp(min: Float=0f, max: Float=1f): Float = Math.max(Math.min(max, this ), min)