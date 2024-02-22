package com.example.wordskotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository, private val preferencesHelper: PreferencesHelper) : ViewModel() {
    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>> get() = _words

    private var totalWordCount = 0
    var currentPage = preferencesHelper.lastIndex

    val pageSize = 10

    init {
        currentPage =
            preferencesHelper.currentPage
        loadWords()
    }

    private fun loadWords() {
        viewModelScope.launch {
            val fetchedWords = repository.fetchWords()
            fetchedWords?.let {
                totalWordCount = it.size
                updateWordsForCurrentPage()
            } ?: kotlin.run {
                _words.value = emptyList()
            }
        }
    }


    private fun updateCurrentPageInPreferences() {
        preferencesHelper.currentPage = currentPage
    }

    suspend fun nextPage() {
        if ((currentPage + 1) * pageSize < totalWordCount) {
            currentPage++
            updateWordsForCurrentPage()
            updateCurrentPageInPreferences()
        }
    }

    suspend fun previousPage() {
        if (currentPage > 0) {
            currentPage--
            updateWordsForCurrentPage()
            updateCurrentPageInPreferences()
        }
    }


    private suspend fun updateWordsForCurrentPage() {
        val startIndex = currentPage * pageSize
        val endIndex = minOf(startIndex + pageSize, totalWordCount)
        _words.value = repository.fetchWords()?.subList(startIndex, endIndex)
    }


}

/*
                Bu Kotlin kodu, Android'de MVVM (Model-View-ViewModel) mimarisine uygun olarak tasarlanmış
                WordViewModel sınıfını tanımlar. WordViewModel, kullanıcı arayüzü ile veri katmanı arasında
                bir köprü görevi görür. Kelimeleri yüklemek, sayfalamayı yönetmek ve kullanıcı tercihlerini
                kaydetmek/yüklemek için sorumludur. İşte bu sınıfın detaylı bir açıklaması:

                Sınıf Tanımı ve Başlangıç Durumu
                WordViewModel sınıfı, ViewModel sınıfından türetilmiştir ve iki parametre alır: WordRepository
                ve PreferencesHelper. Bu parametreler, kelimelerin veri kaynağına erişim ve kullanıcı tercihlerinin
                yönetimi için gereklidir.
                private val _words = MutableLiveData<List<Word>>(): Kelimelerin listesini tutan özel bir
                MutableLiveData nesnesi. Bu nesne, UI katmanının gözlemleyebileceği ve güncellemeleri alabileceği
                LiveData nesnesine dönüştürülür.
                val words: LiveData<List<Word>> get() = _words: Dışarıya açık, gözlemlenebilir LiveData nesnesi.
                UI katmanı bu nesneyi gözlemleyerek veri değişikliklerine tepki verebilir.
                private var totalWordCount = 0: Toplam kelime sayısını tutar.
                var currentPage = preferencesHelper.lastIndex: Geçerli sayfa numarasını tutar. Uygulama başlatıldığında
                kullanıcı tercihlerinden yüklenir.
                Başlangıç İşlemleri
                init bloğu içinde, loadWords() fonksiyonu çağrılarak kelimelerin yüklenmesi işlemi başlatılır.
                Kelimelerin Yüklenmesi
                loadWords(): viewModelScope.launch kullanılarak bir coroutine başlatılır ve repository.fetchWords()
                metodu ile veri katmanından kelimelerin listesi asenkron bir şekilde çekilir. Bu liste, _words LiveData
                nesnesine atanır ve böylece UI katmanında gösterilebilir hale gelir.
                Sayfalama İşlemleri
                nextPage() ve previousPage(): Kullanıcı sayfalar arasında gezinmek istediğinde çağrılan fonksiyonlar.
                Bu fonksiyonlar, currentPage değişkenini günceller ve updateWordsForCurrentPage() metodu ile yeni sayfadaki
                kelimeleri yükler.
                updateWordsForCurrentPage(): Geçerli sayfa numarasına ve sayfa büyüklüğüne (pageSize) göre kelimelerin bir alt
                listesini yükler. Bu, kullanıcının yalnızca görüntülemekte olduğu sayfadaki kelimelerle çalışmasını sağlar ve
                verimliliği artırır.
                Kullanıcı Tercihlerinin Güncellenmesi
                updateCurrentPageInPreferences(): Kullanıcı sayfa değiştirdiğinde, yeni sayfa numarası PreferencesHelper aracılığıyla kalıcı
                 hafızaya kaydedilir. Bu, uygulama yeniden başlatıldığında kullanıcının en son baktığı sayfadan devam edebilmesini sağlar.
                Bu sınıf, kelime listesi gibi dinamik verilerle çalışan uygulamalarda verimli bir şekilde sayfalama yapılmasını,
                kullanıcı tercihlerinin yönetilmesini ve UI ile veri katmanı arasında sorunsuz bir iletişim kurulmasını sağlar.
                MVVM mimarisi ve Kotlin'in modern programlama özellikleri sayesinde, bu sınıf uygulamanın bakımını ve genişletilmesini kolaylaştırır.

*/
