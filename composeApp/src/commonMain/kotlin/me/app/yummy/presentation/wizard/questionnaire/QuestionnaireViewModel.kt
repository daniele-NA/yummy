package me.app.yummy.presentation.wizard.questionnaire

import androidx.lifecycle.ViewModel



// == SECTION N°0 == //
enum class WSkillTypes(val value: String) {
    ITALIAN("Italian"),
    AMERICAN("American"),
    MEXICAN("Mexican"),
    ASIAN("Asian"),
    INDIAN("Indian")
}

// == SECTION N°1 == //
enum class WExperienceLevel(val value: String) {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced")
}

// == SECTION N°2 == //
enum class WDietaryRestrictions(val value: String) {
    GLUTEN_FREE("Gluten-Free"),
    LACTOSE_FREE("Lactose-Free"),
    VEGAN("Vegan"),
    VEGETARIAN("Vegetarian"),
    EGG_FREE("Egg-Free"),
    NUT_FREE("Nut-Free"),
    NONE("None")
}

// == SECTION N°3 == //
enum class WGoal(val value: String) {
    SAVE("Save my recipes"),
    DISCOVER("Discover new recipes"),
    SHARE("Share recipes with others")
}


// == HANDLE OPERATIONS ON QUESTIONNAIRE == //
/* TODO USE VALUES FOR API OR STUFF LIKE THAT */
class QuestionnaireViewModel: ViewModel() {


    internal var skill: WSkillTypes? = null
    internal var experience: WExperienceLevel? = null
    internal var dietary: WDietaryRestrictions? = null
    internal var goal: WGoal? = null

    internal fun saveSkill(value: WSkillTypes) { skill = value }
    internal fun saveExperience(value: WExperienceLevel) { experience = value }
    internal fun saveDietary(value: WDietaryRestrictions) { dietary = value }
    internal fun saveGoal(value: WGoal) { goal = value }

}