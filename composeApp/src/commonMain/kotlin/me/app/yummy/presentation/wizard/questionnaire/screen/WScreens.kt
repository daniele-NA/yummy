package me.app.yummy.presentation.wizard.questionnaire.screen

import me.app.yummy.presentation.wizard.questionnaire.WGoal
import me.app.yummy.presentation.wizard.questionnaire.WDietaryRestrictions
import me.app.yummy.presentation.wizard.questionnaire.WExperienceLevel
import me.app.yummy.presentation.wizard.questionnaire.WSkillTypes
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.app.yummy.compose.widgets.buttons.AppButton

@Composable
fun WSkillScreen(next: (WSkillTypes) -> Unit) {
    WSkillTypes.entries.forEach { type ->
        AppButton(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            onClick = { next(type) },
            text = type.value
        )
    }
}

@Composable
fun WExperienceScreen(next: (WExperienceLevel) -> Unit) {
    WExperienceLevel.entries.forEach { level ->
        AppButton(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            onClick = { next(level) },
            text = level.value
        )
    }
}

@Composable
fun WDietaryScreen(next: (WDietaryRestrictions) -> Unit) {
    WDietaryRestrictions.entries.forEach { diet ->
        AppButton(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            onClick = { next(diet) },
            text = diet.value
        )
    }
}

@Composable
fun WGoalScreen(next: (WGoal) -> Unit) {
    WGoal.entries.forEach { goal ->
        AppButton(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            onClick = { next(goal) }, text = goal.value)
    }
}
