package com.example.clockappbyrohan.presentation.ComposableScreens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.clockappbyrohan.R
import com.example.clockappbyrohan.data.offline.alarm.Alarms
import com.example.clockappbyrohan.domain.Functions.getMsFromHoursAndMinutes
import com.example.clockappbyrohan.presentation.ViewModels.SettingViewModel
import com.example.clockappbyrohan.ui.theme.CardBackgroundBlack
import com.example.clockappbyrohan.ui.theme.CustomFontFamilyInter
import com.example.clockappbyrohan.ui.theme.CustomFontFamilyKanit
import com.example.clockappbyrohan.ui.theme.CustomFontFamilyKarla
import com.example.clockappbyrohan.ui.theme.CustomFontFamilyPacifico
import com.example.clockappbyrohan.ui.theme.CustomThemeKarla
import com.example.clockappbyrohan.ui.theme.CustomThemePacifico
import com.example.clockappbyrohan.ui.theme.MainTextColorBlack
import com.example.clockappbyrohan.ui.theme.MainTextColorOrange
import com.example.clockappbyrohan.ui.theme.MainTextColorWhite
import com.example.clockappbyrohan.ui.theme.SecondaryTextColorOrange



@Composable
fun SettingScreenPage(
    modifier: Modifier ,
    context: Context,
    navController: NavHostController,
    viewModel: SettingViewModel,
    cardContainerColor: Color ,
    backgroundColor: Color ,
    fontColor: Color ,
    secondaryFontColor: Color
) {
    /**
     *      1. color =                  three options { Orange(Default) , White , Black }
     *      2. backgroundColor =        two options { White , Black(Default) }
     *      3. font =                   four options { Karla(Default) , Kanit , Pacifico , Inter }
     */
    var choosedColor  = viewModel.choosedColor.collectAsState()
    var choosedBackgroundColor = viewModel.choosedBackgroundColor.collectAsState()
    var choosedFont = viewModel.choosedFont.collectAsState()

    val fontList = listOf("Karla", "Kanit", "Pacifico", "Inter")
    val backgroundColorList = listOf("White", "Black")
    val fontColorList = listOf("Orange", "White", "Black")


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        var configuration = LocalConfiguration.current
        var isPortrait =
            configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
        if (isPortrait) {
            // for Portrait
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(.15f))
                Text(
                    text = AnnotatedString(
                        text = stringResource(id = R.string.app_name), spanStyle = SpanStyle(
                            fontSize = 40.sp, fontWeight = FontWeight.SemiBold
                        )
                    ).plus(AnnotatedString(text = " settings")),
                    color = fontColor,
                    fontSize = 35.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .weight(.25f), colors = CardDefaults.cardColors(
                        containerColor = cardContainerColor,
                        contentColor = backgroundColor,
                        disabledContainerColor = backgroundColor,
                        disabledContentColor = backgroundColor
                    ), shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Font",
                            color = fontColor,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp)
                        )
                        Row(
                            Modifier.fillMaxWidth(.9f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = choosedFont.value == "Karla", onClick = {
                                    viewModel.setChoosedFont("Karla")
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "Karla",
                                    spanStyle = SpanStyle(
                                        fontFamily = CustomFontFamilyKarla,
                                        fontSize = 25.sp,
                                        color = fontColor
                                    )
                                )
                            )
                            Spacer(modifier = Modifier.size(7.dp))
                            RadioButton(
                                selected = choosedFont.value == "Kanit", onClick = {
                                    viewModel.setChoosedFont("Kanit")
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "Kanit",
                                    spanStyle = SpanStyle(
                                        fontFamily = CustomFontFamilyKanit,
                                        fontSize = 25.sp,
                                        color = fontColor
                                    )
                                )
                            )

                        }
                        Row(
                            Modifier.fillMaxWidth(.9f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = choosedFont.value == "Pacifico", onClick = {
                                    viewModel.setChoosedFont("Pacifico")
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "Pacifico",
                                    spanStyle = SpanStyle(
                                        fontFamily = CustomFontFamilyPacifico,
                                        fontSize = 25.sp,
                                        color = fontColor
                                    )
                                )
                            )
                            RadioButton(
                                selected = choosedFont.value == "Inter", onClick = {
                                    viewModel.setChoosedFont("Inter")
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "Inter",
                                    spanStyle = SpanStyle(
                                        fontFamily = CustomFontFamilyInter,
                                        fontSize = 25.sp,
                                        color = fontColor
                                    )
                                )
                            )

                        }
                    }

                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .weight(.15f), colors = CardDefaults.cardColors(
                        containerColor = cardContainerColor,
                        contentColor = backgroundColor,
                        disabledContainerColor = backgroundColor,
                        disabledContentColor = backgroundColor
                    ), shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Background Color",
                            color = fontColor,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp)
                        )
                        Row(
                            Modifier.fillMaxWidth(.9f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = choosedBackgroundColor.value == "Black", onClick = {
                                     viewModel.setChoosedBackgroundColor("Black")
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "Black",
                                    spanStyle = SpanStyle(
                                        fontSize = 25.sp,
                                        color = fontColor
                                    )
                                )
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            RadioButton(
                                selected = choosedBackgroundColor.value == "White", onClick = {
                                     viewModel.setChoosedBackgroundColor("White")
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "White",
                                    spanStyle = SpanStyle(
                                        fontSize = 25.sp,
                                        color = fontColor
                                    )
                                )
                            )

                        }
                    }

                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .weight(.2f), colors = CardDefaults.cardColors(
                        containerColor = cardContainerColor,
                        contentColor = backgroundColor,
                        disabledContainerColor = backgroundColor,
                        disabledContentColor = backgroundColor
                    ), shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Font Color",
                            color = fontColor,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp)
                        )
                        Row(
                            Modifier.fillMaxWidth(.9f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = choosedColor.value == "Orange", onClick = {
                                    viewModel.setChoosedColor("Orange")
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "Orange",
                                    spanStyle = SpanStyle(
                                        fontSize = 25.sp,
                                        color = fontColor
                                    )
                                )
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            RadioButton(
                                selected = choosedColor.value == "White", onClick = {
                                    viewModel.setChoosedColor("White")
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "White",
                                    spanStyle = SpanStyle(
                                        fontSize = 25.sp,
                                        color = fontColor
                                    )
                                )
                            )

                        }
                        Row(
                            Modifier.fillMaxWidth(.9f),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.fillMaxWidth(.22f))
                            RadioButton(
                                selected = choosedColor.value == "Black", onClick = {
                                    viewModel.setChoosedColor("Black")
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "Black",
                                    spanStyle = SpanStyle(
                                        fontSize = 25.sp,
                                        color = fontColor
                                    )
                                )
                            )


                        }

                    }

                }
                Button(
                    onClick = {
                        viewModel.savedClicked(fontToSet = choosedFont.value, backgroundColorToSet = choosedBackgroundColor.value, colorToSet =  choosedColor.value)
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = cardContainerColor
                    ), shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Save",
                        fontSize = 24.sp,
                        color = fontColor
                    )
                }
                Spacer(modifier = Modifier.weight(.15f))

            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(.03f))
                Text(
                    text = AnnotatedString(
                        text = stringResource(id = R.string.app_name), spanStyle = SpanStyle(
                            fontSize = 30.sp, fontWeight = FontWeight.SemiBold
                        )
                    ).plus(AnnotatedString(text = " settings")),
                    color = fontColor,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 45.dp)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .weight(.25f), colors = CardDefaults.cardColors(
                        containerColor = cardContainerColor,
                        contentColor = backgroundColor,
                        disabledContainerColor = backgroundColor,
                        disabledContentColor = backgroundColor
                    ), shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Font",
                            color = fontColor,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W500
                        )
                        Column {
                            Row(
                                Modifier.fillMaxWidth(.9f),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = choosedFont.value == "Karla", onClick = {
                                        viewModel.setChoosedFont("Karla")

                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = fontColor,
                                        unselectedColor = fontColor
                                    )
                                )
                                Text(
                                    text = AnnotatedString(
                                        text = "Karla",
                                        spanStyle = SpanStyle(
                                            fontFamily = CustomFontFamilyKarla,
                                            fontSize = 22.sp,
                                            color = fontColor
                                        )
                                    )
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                RadioButton(
                                    selected = choosedFont.value == "Kanit", onClick = {
                                        viewModel.setChoosedFont("Kanit")

                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = fontColor,
                                        unselectedColor = fontColor
                                    )
                                )
                                Text(
                                    text = AnnotatedString(
                                        text = "Kanit",
                                        spanStyle = SpanStyle(
                                            fontFamily = CustomFontFamilyKanit,
                                            fontSize = 22.sp,
                                            color = fontColor
                                        )
                                    )
                                )

                            }
                            Row(
                                Modifier.fillMaxWidth(.9f),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = choosedFont.value == "Pacifico", onClick = {
                                        viewModel.setChoosedFont("Pacifico")

                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = fontColor,
                                        unselectedColor = fontColor
                                    )
                                )
                                Text(
                                    text = AnnotatedString(
                                        text = "Pacifico",
                                        spanStyle = SpanStyle(
                                            fontFamily = CustomFontFamilyPacifico,
                                            fontSize = 22.sp,
                                            color = fontColor
                                        )
                                    )
                                )
                                Spacer(modifier = Modifier.size(1.dp))
                                RadioButton(
                                    selected = choosedFont.value == "Inter", onClick = {
                                        viewModel.setChoosedFont("Inter")

                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = fontColor,
                                        unselectedColor = fontColor
                                    )
                                )
                                Text(
                                    text = AnnotatedString(
                                        text = "Inter",
                                        spanStyle = SpanStyle(
                                            fontFamily = CustomFontFamilyInter,
                                            fontSize = 22.sp,
                                            color = fontColor
                                        )
                                    )
                                )

                            }
                        }
                    }

                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .weight(.15f), colors = CardDefaults.cardColors(
                        containerColor = cardContainerColor,
                        contentColor = backgroundColor,
                        disabledContainerColor = backgroundColor,
                        disabledContentColor = backgroundColor
                    ), shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Background Color",
                            color = fontColor,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W500,
                        )
                        Row(
                            Modifier.fillMaxWidth(.9f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = choosedBackgroundColor.value == "Black", onClick = {
                                    viewModel.setChoosedBackgroundColor("Black")

                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "Black",
                                    spanStyle = SpanStyle(
                                        fontSize = 22.sp,
                                        color = fontColor
                                    )
                                )
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            RadioButton(
                                selected = choosedBackgroundColor.value == "White", onClick = {
                                    viewModel.setChoosedBackgroundColor("White")

                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "White",
                                    spanStyle = SpanStyle(
                                        fontSize = 22.sp,
                                        color = fontColor
                                    )
                                )
                            )

                        }
                    }

                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .weight(.2f), colors = CardDefaults.cardColors(
                        containerColor = cardContainerColor,
                        contentColor = backgroundColor,
                        disabledContainerColor = backgroundColor,
                        disabledContentColor = backgroundColor
                    ), shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly

                    ) {
                        Text(
                            text = "Font Color",
                            color = fontColor,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W500
                        )


                        Row(
                            Modifier.fillMaxWidth(.9f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = choosedColor.value == "Orange", onClick = {
                                    viewModel.setChoosedColor("Orange")

                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "Orange",
                                    spanStyle = SpanStyle(
                                        fontSize = 22.sp,
                                        color = fontColor
                                    )
                                )
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            RadioButton(
                                selected = choosedColor.value == "White", onClick = {
                                    viewModel.setChoosedColor("White")

                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "White",
                                    spanStyle = SpanStyle(
                                        fontSize = 22.sp,
                                        color = fontColor
                                    )
                                )
                            )


                            RadioButton(
                                selected = choosedColor.value == "Black", onClick = {
                                    viewModel.setChoosedColor("Black")

                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = fontColor,
                                    unselectedColor = fontColor
                                )
                            )
                            Text(
                                text = AnnotatedString(
                                    text = "Black",
                                    spanStyle = SpanStyle(
                                        fontSize = 22.sp,
                                        color = fontColor
                                    )
                                )
                            )


                        }

                    }

                }
                Button(
                    onClick = {
                        viewModel.savedClicked(fontToSet = choosedFont.value, backgroundColorToSet = choosedBackgroundColor.value, colorToSet =  choosedColor.value)
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = cardContainerColor
                    ), shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Save",
                        fontSize = 20.sp,
                        color = fontColor
                    )
                }
                Spacer(modifier = Modifier.weight(.03f))

            }

        }
    }


}