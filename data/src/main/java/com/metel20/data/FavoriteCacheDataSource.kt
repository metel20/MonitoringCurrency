package com.metel20.data


interface FavoriteCacheDataSource {
    interface Save {
        suspend fun save(value: String)
    }

    interface Read {
        suspend fun read(): List<String>
    }

    interface Mutable : Save, Read


    class Base() : Mutable {
        override suspend fun save(value: String) {
            TODO("Not yet implemented")
        }

        override suspend fun read(): List<String> {
            TODO("Not yet implemented")
        }

    }
}