package com.portfolio.hanmo.hanmo.DataModel

import io.realm.RealmList

/**
 * Created by hanmo on 2018. 3. 7..
 */
data class SearchHistory (val id: Int, val search_history: String?, val search_history_time: Long?, val techstack_table: RealmList<TechStackTable>?)