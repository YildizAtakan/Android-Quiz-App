package com.bahriatakanyildiz.finalproject

object QuestionInitializer {

    fun initializeQuestions(questionList: ArrayList<Question>) {

        questionList.add(
            Question(
                "Which one is the capital of Turkey?",
                2,
                "Izmir", "Ankara",
                "Erzincan", "Sivas"
            )
        )

        questionList.add(
            Question(
                "8x11=??",
                2,
                "66", "88",
                "99", "77"
            )
        )

        questionList.add(
            Question(
                "2x22=??",
                1,
                "44", "88",
                "99", "77"
            )
        )
        questionList.add(
            Question(
                "5x12=??",
                3,
                "40", "50",
                "60", "70"
            )
        )


        questionList.add(
            Question(
                "5x5?",
                3,
                "5", "15",
                "25", "35"
            )
        )

        questionList.add(
            Question(
                "Mercekler ışığın hangi özelliği kullanılarak yapılmıştır?",
                1,
                "Kırılma", "Yayılma",
                "Kopma", "Gerilme"
            )
        )


        questionList.add(
            Question(
                "7x7?",
                1,
                "49", "59",
                "69", "79"
            )
        )


        questionList.add(
            Question(
                "4x8?",
                1,
                "32", "34",
                "28", "36"
            )
        )


        questionList.add(
            Question(
                "A magnet would most likely attract which of the following?",
                4,
                "Plastic", "Wood",
                "Water", "Metal"
            )
        )
        questionList.add(
            Question(
                "Which one is the capital of France?",
                4,
                "Berlin", "Luxembourg",
                "Madrid", "Paris"
            )
        )

        /*questionList.add(
            Question(
                "In the United States, what is traditionally the proper way to address a judge?",
                2,
                "Your holiness", "Your Honor",
                "Your eminence", "You da man!"
            )
        )*/
        questionList.add(
            Question(
                "Which of these names is not in the title of a Shakespeare play?",
                4,
                "Hamlet", "Romeo",
                "Macbeth", "Darren"
            )
        )
        questionList.add(
            Question(
                "Which of these pairs of apps offers roughly the same type of service?",
                4,
                "Snapchat and Grubhub", "Whatsapp and SHAREit",
                "TikTok and Spotify", "Lyft and Uber"
            )
        )
    }
}