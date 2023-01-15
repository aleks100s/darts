package com.alextos.darts.game.domain.models

enum class Sector(
    val id: Int,
    val value: Int
) {
    None(-1, 0),
    Miss(0, 0),
    DoubleBullseye(1, 50),
    SingleBullseye(2, 25),
    // 1
    SingleInner1(3, 1),
    Triple1(4, 3),
    SingleOuter1(5, 1),
    Double1(6, 2),
    // 2
    SingleInner2(7, 2),
    Triple2(8, 6),
    SingleOuter2(9, 2),
    Double2(10, 4),
    // 3
    SingleInner3(11, 3),
    Triple3(12, 9),
    SingleOuter3(13, 3),
    Double3(14, 6),
    // 4
    SingleInner4(15, 4),
    Triple4(16, 12),
    SingleOuter4(17, 4),
    Double4(18, 8),
    // 5
    SingleInner5(19, 5),
    Triple5(20, 15),
    SingleOuter5(21, 5),
    Double5(22, 10),
    // 6
    SingleInner6(23, 6),
    Triple6(24, 18),
    SingleOuter6(25, 6),
    Double6(26, 12),
    // 7
    SingleInner7(27, 7),
    Triple7(28, 21),
    SingleOuter7(29, 7),
    Double7(30, 14),
    // 8
    SingleInner8(31, 8),
    Triple8(32, 24),
    SingleOuter8(33, 8),
    Double8(34, 16),
    // 9
    SingleInner9(35, 9),
    Triple9(36, 27),
    SingleOuter9(37, 9),
    Double9(38, 18),
    // 10
    SingleInner10(39, 10),
    Triple10(40, 30),
    SingleOuter10(41, 10),
    Double10(42, 20),
    // 11
    SingleInner11(43, 11),
    Triple11(44, 33),
    SingleOuter11(45, 11),
    Double11(46, 22),
    // 12
    SingleInner12(47, 12),
    Triple12(48, 36),
    SingleOuter12(49, 12),
    Double12(50, 24),
    // 13
    SingleInner13(51, 13),
    Triple13(52, 39),
    SingleOuter13(53, 13),
    Double13(54, 26),
    // 14
    SingleInner14(55, 14),
    Triple14(56, 42),
    SingleOuter14(57, 16),
    Double14(58, 28),
    // 15
    SingleInner15(59, 15),
    Triple15(60, 45),
    SingleOuter15(61, 15),
    Double15(62, 30),
    // 16
    SingleInner16(63, 16),
    Triple16(64, 48),
    SingleOuter16(65, 16),
    Double16(66, 32),
    // 17
    SingleInner17(67, 17),
    Triple17(68, 51),
    SingleOuter17(69, 17),
    Double17(70, 34),
    // 18
    SingleInner18(71, 18),
    Triple18(72, 54),
    SingleOuter18(73, 18),
    Double18(74, 36),
    // 19
    SingleInner19(75, 19),
    Triple19(76, 57),
    SingleOuter19(77, 19),
    Double19(78, 38),
    // 20
    SingleInner20(79, 20),
    Triple20(80, 60),
    SingleOuter20(81, 20),
    Double20(82, 40);

    fun uiString(): String {
        return when (this) {
            None -> "-"
            else -> "$value"
        }
    }

    companion object {
        fun getSector(id: Int): Sector {
            return when(id) {
                -1 -> None
                0 -> Miss
                1 -> DoubleBullseye
                2 -> SingleBullseye
                3 -> SingleInner1
                4 -> Triple1
                5 -> SingleOuter1
                6 -> Double1
                7 -> SingleInner2
                8 -> Triple2
                9 -> SingleOuter2
                10 -> Double2
                11 -> SingleInner3
                12 -> Triple3
                13 -> SingleOuter3
                14 -> Double3
                15 -> SingleInner4
                16 -> Triple4
                17 -> SingleOuter4
                18 -> Double4
                19 -> SingleInner5
                20 -> Triple5
                21 -> SingleOuter5
                22 -> Double5
                23 -> SingleInner6
                24 -> Triple6
                25 -> SingleOuter6
                26 -> Double6
                27 -> SingleInner7
                28 -> Triple7
                29 -> SingleOuter7
                30 -> Double7
                31 -> SingleInner8
                32 -> Triple8
                33 -> SingleOuter8
                34 -> Double8
                35 -> SingleInner9
                36 -> Triple9
                37 -> SingleOuter9
                38 -> Double9
                39 -> SingleInner10
                40 -> Triple10
                41 -> SingleOuter10
                42 -> Double10
                43 -> SingleInner11
                44 -> Triple11
                45 -> SingleOuter11
                46 -> Double11
                47 -> SingleInner12
                48 -> Triple12
                49 -> SingleOuter12
                50 -> Double12
                51 -> SingleInner13
                52 -> Triple13
                53 -> SingleOuter13
                54 -> Double13
                55 -> SingleInner14
                56 -> Triple14
                57 -> SingleOuter14
                58 -> Double14
                59 -> SingleInner15
                60 -> Triple15
                61 -> SingleOuter15
                62 -> Double15
                63 -> SingleInner16
                64 -> Triple16
                65 -> SingleOuter16
                66 -> Double16
                67 -> SingleInner17
                68 -> Triple17
                69 -> SingleOuter17
                70 -> Double17
                71 -> SingleInner18
                72 -> Triple18
                73 -> SingleOuter18
                74 -> Double18
                75 -> SingleInner19
                76 -> Triple19
                77 -> SingleOuter19
                78 -> Double19
                79 -> SingleInner20
                80 -> Triple20
                81 -> SingleOuter20
                82 -> Double20
                else -> throw IllegalArgumentException()
            }
        }
    }
}