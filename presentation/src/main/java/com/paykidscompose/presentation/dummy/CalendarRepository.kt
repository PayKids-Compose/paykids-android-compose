package com.paykidscompose.presentation.dummy

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class DummyDataManager {

    // AllowanceChartDTO 리스트 (가계부 내역)
    private val _allowanceChartList: MutableList<AllowanceChartDTO> = mutableListOf()
    // CategoryDTO 리스트 (카테고리 목록)
    private val _categoryList: MutableList<CategoryDTO> = mutableListOf()

    // 다음 가계부 ID를 위한 변수
    private var nextAllowanceId: Int = 1
    // 다음 카테고리 ID를 위한 변수
    private var nextCategoryId: Int = 1

    init {
        // 초기 더미 카테고리 데이터 추가 (Optional)
        setCategory(CategoryDTO(nextCategoryId++, "식비", AllowanceType.EXPENSE))
        setCategory(CategoryDTO(nextCategoryId++, "교통비", AllowanceType.EXPENSE))
        setCategory(CategoryDTO(nextCategoryId++, "문화생활", AllowanceType.EXPENSE))
        setCategory(CategoryDTO(nextCategoryId++, "급여", AllowanceType.INCOME))
        setCategory(CategoryDTO(nextCategoryId++, "용돈", AllowanceType.INCOME))

        // 초기 더미 가계부 내역 데이터 추가 (Optional)
        setAllowanceChart(AllowanceChartDTO(nextAllowanceId++, "2025-05-10", AllowanceType.EXPENSE, "식비", 15000, "점심 식사"))
        setAllowanceChart(AllowanceChartDTO(nextAllowanceId++, "2025-05-10", AllowanceType.EXPENSE, "교통비", 1250, "버스"))
        setAllowanceChart(AllowanceChartDTO(nextAllowanceId++, "2025-05-15", AllowanceType.INCOME, "급여", 2000000, "5월 급여"))
        setAllowanceChart(AllowanceChartDTO(nextAllowanceId++, "2025-05-20", AllowanceType.EXPENSE, "문화생활", 30000, "영화 관람"))
        setAllowanceChart(AllowanceChartDTO(nextAllowanceId++, "2025-06-01", AllowanceType.EXPENSE, "식비", 10000, "아침 식사"))
        setAllowanceChart(AllowanceChartDTO(nextAllowanceId++, "2025-06-05", AllowanceType.INCOME, "용돈", 50000, "부모님 용돈"))
        setAllowanceChart(AllowanceChartDTO(nextAllowanceId++, "2025-06-05", AllowanceType.EXPENSE, "식비", 5000, "편의점 간식"))
        setAllowanceChart(AllowanceChartDTO(nextAllowanceId++, "2025-06-05", AllowanceType.EXPENSE, "식비", 7000, "커피"))
        setAllowanceChart(AllowanceChartDTO(nextAllowanceId++, "2025-06-05", AllowanceType.EXPENSE, "문화생활", 15000, "책 구매"))
        setAllowanceChart(AllowanceChartDTO(nextAllowanceId++, "2025-06-10", AllowanceType.EXPENSE, "교통비", 2500, "택시"))
    }

    /**
     * **[Set 함수] 카테고리 데이터를 추가합니다.**
     *
     * @param category (CategoryDTO): 추가할 카테고리 정보를 담은 객체입니다.
     */
    fun setCategory(category: CategoryDTO) {
        _categoryList.add(category)
    }

    /**
     * **[Set 함수] 가계부 내역(수입/소비) 데이터를 추가합니다.**
     *
     * @param allowanceChart (AllowanceChartDTO): 추가할 가계부 내역 정보를 담은 객체입니다.
     */
    fun setAllowanceChart(allowanceChart: AllowanceChartDTO) {
        _allowanceChartList.add(allowanceChart)
    }

    // --- 소비 관련 Get/Set 함수 ---

    /**
     * **[Get 함수] 월 소비 전체 금액을 조회합니다.**
     *
     * @param yearMonth (String): 조회할 연월 (예: "2025-05")
     * @return (Int): 해당 월의 총 소비 금액
     */
    fun getMonthlyTotalExpense(yearMonth: String): Int {
        return _allowanceChartList.filter {
            it.allowanceType == AllowanceType.EXPENSE && it.date.startsWith(yearMonth)
        }.sumOf { it.amount }
    }

    /**
     * **[Get 함수] 월 카테고리 중 최다 소비 카테고리를 조회합니다.**
     *
     * @param yearMonth (String): 조회할 연월 (예: "2025-05")
     * @return (AllowanceChartCategoryDto?): 최다 소비 카테고리 정보를 담은 객체입니다. (없으면 null)
     */
    fun getMonthlyMostSpentCategory(yearMonth: String): AllowanceChartCategoryDto? {
        val monthlyExpenses = _allowanceChartList.filter {
            it.allowanceType == AllowanceType.EXPENSE && it.date.startsWith(yearMonth)
        }

        if (monthlyExpenses.isEmpty()) {
            return null
        }

        val categorySpending = monthlyExpenses.groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }

        val totalMonthlyExpense = monthlyExpenses.sumOf { it.amount }
        if (totalMonthlyExpense == 0) return null // 총 소비가 0인 경우 방지

        val mostSpentCategoryEntry = categorySpending.maxByOrNull { it.value }

        return mostSpentCategoryEntry?.let { (category, amount) ->
            val percent = if (totalMonthlyExpense > 0) {
                String.format("%.1f%%", (amount.toDouble() / totalMonthlyExpense) * 100)
            } else {
                "0.0%"
            }
            AllowanceChartCategoryDto(AllowanceType.EXPENSE, category, percent, amount)
        }
    }

    /**
     * **[Get 함수] 월, 일별 소비 금액을 조회합니다.**
     *
     * @param yearMonth (String): 조회할 연월 (예: "2025-05")
     * @return (List<AllowanceChartAmountDTO>): 해당 월의 일별 소비 금액 리스트입니다.
     * 각 날짜에 해당하는 소비 금액이 없으면 0으로 처리됩니다.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthlyDailyExpense(yearMonth: String): List<AllowanceChartAmountDTO> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val targetYearMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"))
        val startDate = targetYearMonth.atDay(1)
        val endDate = targetYearMonth.atEndOfMonth()

        val dailyExpensesMap = _allowanceChartList
            .filter { it.allowanceType == AllowanceType.EXPENSE && it.date.startsWith(yearMonth) }
            .groupBy { it.date }
            .mapValues { entry -> entry.value.sumOf { it.amount } }
            .toMutableMap()

        val resultList = mutableListOf<AllowanceChartAmountDTO>()
        var currentDate = startDate
        while (!currentDate.isAfter(endDate)) {
            val dateString = currentDate.format(formatter)
            val amount = dailyExpensesMap[dateString] ?: 0
            resultList.add(AllowanceChartAmountDTO(dateString, amount, AllowanceType.EXPENSE))
            currentDate = currentDate.plusDays(1)
        }
        return resultList
    }

    /**
     * **[Get 함수] 월, 특정 카테고리 소비를 조회합니다.**
     *
     * @param yearMonth (String): 조회할 연월 (예: "2025-05")
     * @param category (String): 조회할 카테고리명 (예: "식비")
     * @return (List<AllowanceChartDTO>): 해당 월의 특정 카테고리 소비 내역 리스트입니다.
     */
    fun getMonthlySpecificCategoryExpense(yearMonth: String, category: String): List<AllowanceChartDTO> {
        return _allowanceChartList.filter {
            it.allowanceType == AllowanceType.EXPENSE && it.date.startsWith(yearMonth) && it.category == category
        }
    }

    /**
     * **[Get 함수] 월 모든 카테고리 소비를 조회합니다.**
     *
     * @param yearMonth (String): 조회할 연월 (예: "2025-05")
     * @return (List<AllowanceChartCategoryDto>): 해당 월의 각 카테고리별 소비 금액 및 비율 리스트입니다.
     */
    fun getMonthlyAllCategoriesExpense(yearMonth: String): List<AllowanceChartCategoryDto> {
        val monthlyExpenses = _allowanceChartList.filter {
            it.allowanceType == AllowanceType.EXPENSE && it.date.startsWith(yearMonth)
        }

        if (monthlyExpenses.isEmpty()) {
            return emptyList()
        }

        val totalMonthlyExpense = monthlyExpenses.sumOf { it.amount }
        if (totalMonthlyExpense == 0) return emptyList()

        return monthlyExpenses.groupBy { it.category }
            .map { (category, allowances) ->
                val amount = allowances.sumOf { it.amount }
                val percent = String.format("%.1f%%", (amount.toDouble() / totalMonthlyExpense) * 100)
                AllowanceChartCategoryDto(AllowanceType.EXPENSE, category, percent, amount)
            }.sortedByDescending { it.amount } // 금액이 높은 순으로 정렬
    }

    /**
     * **[Get 함수] 일 소비를 조회합니다.**
     *
     * @param date (String): 조회할 날짜 (예: "2025-05-10")
     * @return (List<AllowanceChartDTO>): 해당 일의 소비 내역 리스트입니다.
     */
    fun getDailyExpense(date: String): List<AllowanceChartDTO> {
        return _allowanceChartList.filter {
            it.allowanceType == AllowanceType.EXPENSE && it.date == date
        }
    }

    /**
     * **[Set 함수] 소비 내역을 저장합니다.**
     *
     * @param date (String): 날짜 (YYYY-MM-DD)
     * @param category (String): 카테고리
     * @param amount (Int): 금액
     * @param memo (String): 메모
     * @return (Boolean): 저장 성공 여부
     */
    fun saveExpense(date: String, category: String, amount: Int, memo: String): Boolean {
        if (amount < 0) {
            println("저장 실패: 소비 금액은 음수일 수 없습니다.")
            return false
        }
        val newAllowance = AllowanceChartDTO(nextAllowanceId++, date, AllowanceType.EXPENSE, category, amount, memo)
        _allowanceChartList.add(newAllowance)
        println("소비 내역 저장 완료: ${newAllowance.date}, ${newAllowance.category}, ${newAllowance.amount}원, ${newAllowance.memo}")
        return true
    }

    /**
     * **[Set 함수] 소비 내역을 수정합니다.**
     *
     * @param id (Int): 수정할 내역의 고유 ID
     * @param newDate (String): 변경할 날짜 (YYYY-MM-DD)
     * @param newCategory (String): 변경할 카테고리
     * @param newAmount (Int): 변경할 금액
     * @param newMemo (String): 변경할 메모
     * @return (Boolean): 수정 성공 여부
     */
    fun updateExpense(id: Int, newDate: String, newCategory: String, newAmount: Int, newMemo: String): Boolean {
        val index = _allowanceChartList.indexOfFirst { it.id == id && it.allowanceType == AllowanceType.EXPENSE }
        if (index != -1) {
            if (newAmount < 0) {
                println("수정 실패: 소비 금액은 음수일 수 없습니다.")
                return false
            }
            _allowanceChartList[index] = AllowanceChartDTO(id, newDate, AllowanceType.EXPENSE, newCategory, newAmount, newMemo)
            println("소비 내역 수정 완료: ID $id")
            return true
        }
        println("소비 내역을 찾을 수 없거나 소비 내역이 아닙니다: ID $id")
        return false
    }

    /**
     * **[Set 함수] 소비 내역을 삭제합니다.**
     *
     * @param id (Int): 삭제할 내역의 고유 ID
     * @return (Boolean): 삭제 성공 여부
     */
    fun deleteExpense(id: Int): Boolean {
        val initialSize = _allowanceChartList.size
        _allowanceChartList.removeIf { it.id == id && it.allowanceType == AllowanceType.EXPENSE }
        val isDeleted = _allowanceChartList.size < initialSize
        if (isDeleted) {
            println("소비 내역 삭제 완료: ID $id")
        } else {
            println("소비 내역을 찾을 수 없거나 소비 내역이 아닙니다: ID $id")
        }
        return isDeleted
    }

    // --- 수입 관련 Get/Set 함수 ---

    /**
     * **[Set 함수] 수입 내역을 삭제합니다.** (기능 목록에 있었으므로 구현)
     *
     * @param id (Int): 삭제할 내역의 고유 ID
     * @return (Boolean): 삭제 성공 여부
     */
    fun deleteIncome(id: Int): Boolean {
        val initialSize = _allowanceChartList.size
        _allowanceChartList.removeIf { it.id == id && it.allowanceType == AllowanceType.INCOME }
        val isDeleted = _allowanceChartList.size < initialSize
        if (isDeleted) {
            println("수입 내역 삭제 완료: ID $id")
        } else {
            println("수입 내역을 찾을 수 없거나 수입 내역이 아닙니다: ID $id")
        }
        return isDeleted
    }

    /**
     * **[Get 함수] 월 수입 전체 금액을 조회합니다.**
     *
     * @param yearMonth (String): 조회할 연월 (예: "2025-05")
     * @return (Int): 해당 월의 총 수입 금액
     */
    fun getMonthlyTotalIncome(yearMonth: String): Int {
        return _allowanceChartList.filter {
            it.allowanceType == AllowanceType.INCOME && it.date.startsWith(yearMonth)
        }.sumOf { it.amount }
    }

    /**
     * **[Get 함수] 월, 일별 수입 금액을 조회합니다.**
     *
     * @param yearMonth (String): 조회할 연월 (예: "2025-05")
     * @return (List<AllowanceChartAmountDTO>): 해당 월의 일별 수입 금액 리스트입니다.
     * 각 날짜에 해당하는 수입 금액이 없으면 0으로 처리됩니다.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthlyDailyIncome(yearMonth: String): List<AllowanceChartAmountDTO> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val targetYearMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"))
        val startDate = targetYearMonth.atDay(1)
        val endDate = targetYearMonth.atEndOfMonth()

        val dailyIncomesMap = _allowanceChartList
            .filter { it.allowanceType == AllowanceType.INCOME && it.date.startsWith(yearMonth) }
            .groupBy { it.date }
            .mapValues { entry -> entry.value.sumOf { it.amount } }
            .toMutableMap()

        val resultList = mutableListOf<AllowanceChartAmountDTO>()
        var currentDate = startDate
        while (!currentDate.isAfter(endDate)) {
            val dateString = currentDate.format(formatter)
            val amount = dailyIncomesMap[dateString] ?: 0
            resultList.add(AllowanceChartAmountDTO(dateString, amount, AllowanceType.INCOME))
            currentDate = currentDate.plusDays(1)
        }
        return resultList
    }

    /**
     * **[Get 함수] 월, 특정 카테고리 수입을 조회합니다.**
     *
     * @param yearMonth (String): 조회할 연월 (예: "2025-05")
     * @param category (String): 조회할 카테고리명 (예: "급여")
     * @return (List<AllowanceChartDTO>): 해당 월의 특정 카테고리 수입 내역 리스트입니다.
     */
    fun getMonthlySpecificCategoryIncome(yearMonth: String, category: String): List<AllowanceChartDTO> {
        return _allowanceChartList.filter {
            it.allowanceType == AllowanceType.INCOME && it.date.startsWith(yearMonth) && it.category == category
        }
    }

    /**
     * **[Get 함수] 월, 모든 카테고리 수입을 조회합니다.**
     *
     * @param yearMonth (String): 조회할 연월 (예: "2025-05")
     * @return (List<AllowanceChartCategoryDto>): 해당 월의 각 카테고리별 수입 금액 및 비율 리스트입니다.
     */
    fun getMonthlyAllCategoriesIncome(yearMonth: String): List<AllowanceChartCategoryDto> {
        val monthlyIncomes = _allowanceChartList.filter {
            it.allowanceType == AllowanceType.INCOME && it.date.startsWith(yearMonth)
        }

        if (monthlyIncomes.isEmpty()) {
            return emptyList()
        }

        val totalMonthlyIncome = monthlyIncomes.sumOf { it.amount }
        if (totalMonthlyIncome == 0) return emptyList()

        return monthlyIncomes.groupBy { it.category }
            .map { (category, allowances) ->
                val amount = allowances.sumOf { it.amount }
                val percent = String.format("%.1f%%", (amount.toDouble() / totalMonthlyIncome) * 100)
                AllowanceChartCategoryDto(AllowanceType.INCOME, category, percent, amount)
            }.sortedByDescending { it.amount } // 금액이 높은 순으로 정렬
    }

    /**
     * **[Get 함수] 일 수입을 조회합니다.**
     *
     * @param date (String): 조회할 날짜 (예: "2025-05-15")
     * @return (List<AllowanceChartDTO>): 해당 일의 수입 내역 리스트입니다.
     */
    fun getDailyIncome(date: String): List<AllowanceChartDTO> {
        return _allowanceChartList.filter {
            it.allowanceType == AllowanceType.INCOME && it.date == date
        }
    }

    /**
     * **[Set 함수] 수입 내역을 저장합니다.**
     *
     * @param date (String): 날짜 (YYYY-MM-DD)
     * @param category (String): 카테고리
     * @param amount (Int): 금액
     * @param memo (String): 메모
     * @return (Boolean): 저장 성공 여부
     */
    fun saveIncome(date: String, category: String, amount: Int, memo: String): Boolean {
        if (amount < 0) {
            println("저장 실패: 수입 금액은 음수일 수 없습니다.")
            return false
        }
        val newAllowance = AllowanceChartDTO(nextAllowanceId++, date, AllowanceType.INCOME, category, amount, memo)
        _allowanceChartList.add(newAllowance)
        println("수입 내역 저장 완료: ${newAllowance.date}, ${newAllowance.category}, ${newAllowance.amount}원, ${newAllowance.memo}")
        return true
    }

    /**
     * **[Set 함수] 수입 내역을 수정합니다.**
     *
     * @param id (Int): 수정할 내역의 고유 ID
     * @param newDate (String): 변경할 날짜 (YYYY-MM-DD)
     * @param newCategory (String): 변경할 카테고리
     * @param newAmount (Int): 변경할 금액
     * @param newMemo (String): 변경할 메모
     * @return (Boolean): 수정 성공 여부
     */
    fun updateIncome(id: Int, newDate: String, newCategory: String, newAmount: Int, newMemo: String): Boolean {
        val index = _allowanceChartList.indexOfFirst { it.id == id && it.allowanceType == AllowanceType.INCOME }
        if (index != -1) {
            if (newAmount < 0) {
                println("수정 실패: 수입 금액은 음수일 수 없습니다.")
                return false
            }
            _allowanceChartList[index] = AllowanceChartDTO(id, newDate, AllowanceType.INCOME, newCategory, newAmount, newMemo)
            println("수입 내역 수정 완료: ID $id")
            return true
        }
        println("수입 내역을 찾을 수 없거나 수입 내역이 아닙니다: ID $id")
        return false
    }

    // 모든 가계부 내역 조회 (테스트 및 디버깅용)
    fun getAllAllowanceCharts(): List<AllowanceChartDTO> {
        return _allowanceChartList.toList()
    }

    // 모든 카테고리 조회 (테스트 및 디버깅용)
    fun getAllCategories(): List<CategoryDTO> {
        return _categoryList.toList()
    }
}