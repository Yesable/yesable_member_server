package yesable.member.enums.user;

public enum Disabilitytype { //장애인 복지법에서 정의하는 15가지 분류
    // Physical Disabilities - External
    UNKNOWN, //mapping 관련
    PHYSICAL_DISABILITY_MOBILITY_IMPAIRED,       // 지체장애인
    PHYSICAL_DISABILITY_BRAIN_LESSON,            // 뇌병변장애인
    PHYSICAL_DISABILITY_VISUAL_IMPAIRED,         // 시각장애인
    PHYSICAL_DISABILITY_HEARING_IMPAIRED,        // 청각장애인
    PHYSICAL_DISABILITY_SPEECH_IMPAIRED,         // 언어장애인
    PHYSICAL_DISABILITY_FACIAL_IMPAIRED,         // 안면장애인

    // Physical Disabilities - Internal
    PHYSICAL_DISABILITY_KIDNEY_IMPAIRED,         // 신장장애인
    PHYSICAL_DISABILITY_HEART_IMPAIRED,          // 심장장애인
    PHYSICAL_DISABILITY_RESPIRATORY_IMPAIRED,    // 호흡기장애인
    PHYSICAL_DISABILITY_LIVER_IMPAIRED,          // 간기능장애인
    PHYSICAL_DISABILITY_STOMA_IMPAIRED,          // 장루요루장애인
    PHYSICAL_DISABILITY_EPILEPSY_IMPAIRED,       // 간질장애인

    // Mental Disabilities
    MENTAL_DISABILITY_INTELLECTUAL_IMPAIRED,     // 지적장애인
    MENTAL_DISABILITY_MENTAL_IMPAIRED,           // 정신장애인
    MENTAL_DISABILITY_AUTISM_IMPAIRED            // 자폐성장애인
}
