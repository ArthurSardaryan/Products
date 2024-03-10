package vk.tech.products.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun rememberCurrentWindowInfo(): CurrentWindowInfo {
    val currentWidthType = when(LocalConfiguration.current.screenWidthDp) {
        in 0 until 600 -> CurrentWindowInfo.CurrentType.COMPACT
        in 600 until 840 -> CurrentWindowInfo.CurrentType.MEDIUM
        else -> CurrentWindowInfo.CurrentType.EXPANDED
    }
    val currentHeightType = when(LocalConfiguration.current.screenHeightDp) {
        in 0 until 480 -> CurrentWindowInfo.CurrentType.COMPACT
        in 480 until 900 -> CurrentWindowInfo.CurrentType.MEDIUM
        else -> CurrentWindowInfo.CurrentType.EXPANDED
    }
    return CurrentWindowInfo(
        currentHeight = LocalConfiguration.current.screenHeightDp.dp,
        currentWidth = LocalConfiguration.current.screenWidthDp.dp,
        currentWidthType = currentWidthType,
        currentHeightType = currentHeightType,
        currentDp = if (currentWidthType  != CurrentWindowInfo.CurrentType.COMPACT) {
            (1.5).dp
        }
        else {
            1.dp
        },
        currentSp = if (currentWidthType  != CurrentWindowInfo.CurrentType.COMPACT) {
            (1.5).sp
        }
        else {
            1.sp
        }
    )
}
data class CurrentWindowInfo(
    val currentWidthType: CurrentType,
    val currentHeightType: CurrentType,
    val currentWidth: Dp,
    val currentHeight: Dp,
    val currentDp: Dp,
    val currentSp: TextUnit
) {
    enum class CurrentType {
        COMPACT,
        MEDIUM,
        EXPANDED
    }
}