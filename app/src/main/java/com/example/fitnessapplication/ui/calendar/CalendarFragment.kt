package com.example.fitnessapplication.ui.calendar

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.R
import com.example.fitnessapplication.adapters.CalendarSetsAdapter
import com.example.fitnessapplication.adapters.SetsAdapter
import com.example.fitnessapplication.databinding.CalendarDayBinding
import com.example.fitnessapplication.databinding.CalendarHeaderBinding
import com.example.fitnessapplication.databinding.FragmentCalendarBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.Size
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import db.CalendarSetDataEntity
import db.SetDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


class CalendarFragment : Fragment() {

    private val calendarViewModel: CalendarViewModel by activityViewModels()
    private var _binding: FragmentCalendarBinding? = null

    private var selectedDate: LocalDate? = null
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    private lateinit var binding: FragmentCalendarBinding

    lateinit var adapter: CalendarSetsAdapter
    lateinit var list: List<CalendarSetDataEntity>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)


        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(3)
        val lastMonth = currentMonth.plusMonths(3)
        val daysOfWeek = daysOfWeekFromLocale()
        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)

        binding.apply {

            calendarView.apply {
                setup(firstMonth, lastMonth, daysOfWeek.first())
                scrollToMonth(currentMonth)
                val dayWidth = dm.widthPixels / 7
                val dayHeight = (dayWidth * 0.80).toInt() // We don't want a square calendar.
                daySize = Size(dayWidth, dayHeight)
            }

            GlobalScope.launch(Dispatchers.IO) {
                list = calendarViewModel.get()
                Log.d("recycler123", "list size is ${list.size}")
                withContext(Dispatchers.Main) {
                    adapter = CalendarSetsAdapter(list)
                    val recyclerView: RecyclerView = view.findViewById(R.id.plannedSetsRv)
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = adapter
                    Log.d("recycler123", "adapter installed")
                }
            }

            addSetBtn.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_calendar_to_setChooseFragment)
            }

            class DayViewContainer(view: View) : ViewContainer(view) {
                lateinit var day: CalendarDay
                val binding = CalendarDayBinding.bind(view)
                init {
                    view.setOnClickListener {
                        if (day.owner == DayOwner.THIS_MONTH) {
                            if (selectedDate != day.date) {
                                val oldDate = selectedDate
                                selectedDate = day.date
                                val binding = this@CalendarFragment.binding
                                binding.calendarView.notifyDateChanged(day.date)
                                oldDate?.let { binding.calendarView.notifyDateChanged(it) }
                            }
                        }
                    }
                }
            }
            calendarView.dayBinder = object : DayBinder<DayViewContainer> {
                override fun create(view: View) = DayViewContainer(view)
                override fun bind(container: DayViewContainer, day: CalendarDay) {
                    container.day = day
                    val textView = container.binding.dayText
                    val layout = container.binding.dayLayout
                    textView.text = day.date.dayOfMonth.toString()

                    if (day.owner == DayOwner.THIS_MONTH) {
                        textView.setTextColorRes(R.color.grey)
                        layout.setBackgroundResource(if (selectedDate == day.date) R.drawable.calendar_selected_bg else 0)

                    } else {
                        textView.setTextColorRes(R.color.grey_light)
                        layout.background = null
                    }
                }
            }
            class MonthViewContainer(view: View) : ViewContainer(view) {
                val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
            }
            calendarView.monthHeaderBinder = object :
                MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                    // Setup each header day text if we have not done that already.
                    if (container.legendLayout.tag == null) {
                        container.legendLayout.tag = month.yearMonth
                        container.legendLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
                            tv.text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                                .uppercase(Locale.ENGLISH)
                            tv.setTextColorRes(R.color.grey)
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                        }
                        month.yearMonth
                    }
                }
            }

            calendarView.monthScrollListener = { month ->
                val title = "${monthTitleFormatter.format(month.yearMonth)} ${month.yearMonth.year}"
                monthYearText.text = title

                selectedDate?.let {
                    // Clear selection if we scroll to a new month.
                    selectedDate = null
                    calendarView.notifyDateChanged(it)
                }
            }

            nextMonthImage.setOnClickListener {
                calendarView.findFirstVisibleMonth()?.let {
                    calendarView.smoothScrollToMonth(it.yearMonth.next)
                }
            }

            previousMonthImage.setOnClickListener {
                calendarView.findFirstVisibleMonth()?.let {
                    calendarView.smoothScrollToMonth(it.yearMonth.previous)
                }
            }
        }
    }

}