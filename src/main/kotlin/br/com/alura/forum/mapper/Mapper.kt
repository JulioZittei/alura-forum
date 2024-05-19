package br.com.alura.forum.mapper

fun interface Mapper<T, U> {

    fun map(t: T): U
}