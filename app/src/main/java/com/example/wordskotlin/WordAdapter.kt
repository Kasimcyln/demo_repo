package com.example.wordskotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordskotlin.databinding.ItemWordBinding

@Suppress("NAME_SHADOWING")
class WordAdapter(
    private val currentPage: () -> Int,
    private val pageSize: Int,
    private val preferencesHelper: PreferencesHelper
) : ListAdapter<Word, WordAdapter.WordViewHolder>(WordDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding, preferencesHelper)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = getItem(position)
        holder.bind(word, position, currentPage(), pageSize)
    }

    class WordViewHolder(
        private val binding: ItemWordBinding, private val preferencesHelper: PreferencesHelper
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word, position: Int, currentPage: Int, pageSize: Int) {
            val totalPosition = currentPage * pageSize + position + 1
            binding.tvNumber.text = "$totalPosition"
            binding.wordText.text = word.english
            binding.meaningText.text = word.turkish

            binding.checkbox.setOnCheckedChangeListener(null)

            val isChecked = preferencesHelper.getWordCheckState(word.english)
            binding.checkbox.isChecked = isChecked

            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                preferencesHelper.saveWordCheckState(word.english, isChecked)
                word.isChecked = isChecked
            }
        }
    }

    class WordDiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.english == newItem.english
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    }
}


/*
            Bu Kotlin kodu, bir Android uygulamasında RecyclerView için bir adapter tanımlar.
            WordAdapter sınıfı, kullanıcıya bir liste içinde İngilizce kelimeler ve Türkçe anlamlarını göstermek
            için tasarlanmıştır. Adapter, MVVM mimarisine uygun olarak tasarlanmış ve verilerin UI ile nasıl ilişkilendirileceğini yönetir.

            WordAdapter Sınıfı
            WordAdapter sınıfı, ListAdapter sınıfından türemiştir ve Word nesnelerini içeren bir listeyle çalışacak
            şekilde yapılandırılmıştır. ListAdapter, RecyclerView.Adapter'a göre daha gelişmiş bir versiyondur ve
            liste verilerindeki değişiklikleri daha verimli bir şekilde yönetebilmek için DiffUtil kullanır.
            currentPage, pageSize ve preferencesHelper parametreleri constructor üzerinden alınır. Bu parametreler,
             adapter'ın sayfalama işlemlerini yönetmesi ve her kelimenin seçim durumunu saklaması/sorgulaması için kullanılır.
            onCreateViewHolder Metodu
            onCreateViewHolder metodu, her bir liste öğesi için bir ViewHolder oluşturur. Bu metod, liste öğesinin
            layout'unu (ItemWordBinding aracılığıyla) inflate eder ve WordViewHolder'a dönüştürür.
            ItemWordBinding.inflate çağrısı, item_word.xml layout dosyasından bir binding nesnesi oluşturur. Bu nesne,
            layout içindeki view'lara programatik olarak erişim sağlar.
            onBindViewHolder Metodu
            onBindViewHolder metodu, verilen pozisyonda bir liste öğesini bir ViewHolder ile ilişkilendirir. Bu metod,
             WordViewHolder içinde tanımlanan bind metodunu çağırır.
            Her bir kelimenin ve anlamının yanı sıra, kelimenin listenin içindeki toplam pozisyonu (sayfa numarası ve
            sayfa içi pozisyonu hesaplayarak) gösterilir.
            Kelimenin seçim durumu (isChecked), preferencesHelper aracılığıyla kaydedilir ve yüklenir. Bu, kullanıcının
            her bir kelime için öğrenme durumunu işaretlemesini ve bu durumun kalıcı olarak saklanmasını sağlar.
            WordViewHolder Sınıfı
            WordViewHolder, RecyclerView.ViewHolder'dan türetilmiştir ve bir liste öğesinin UI bileşenlerini yönetir.
            bind metodu, bir Word nesnesini alır ve bu nesnenin verilerini UI bileşenlerine bağlar. Ayrıca, kelimenin
            seçim durumunu preferencesHelper kullanarak kaydeder ve yükler.
            WordDiffCallback Sınıfı
            DiffUtil.ItemCallback'ın bir uygulaması olan WordDiffCallback, liste içindeki öğelerin nasıl karşılaştırılacağını
             belirler. Bu, performansı artırır çünkü sadece değişen öğeler güncellenir.
            areItemsTheSame metodu, iki Word nesnesinin aynı öğeyi temsil edip etmediğini kontrol eder (genellikle bir benzersiz
            tanımlayıcıya veya burada olduğu gibi kelimenin İngilizcesine bakarak).
            areContentsTheSame metodu, iki Word nesnesinin içeriğinin aynı olup olmadığını kontrol eder. Eğer içerik aynıysa,
            öğe için güncelleme yapılmasına gerek yoktur.
            Bu adapter, modern Android uygulama geliştirme tekniklerini kullanarak verimli ve kullanıcı dostu bir liste görünümü
            sağlar. Sayfalama, veri bağlama, ve kullanıcı etkileşimleri gibi özellikleri entegre ederek, kullanıcıların İngilizce
            kelimeleri öğrenmesine ve takip etmesine yardımcı olur.
*/