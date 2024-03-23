package school.redrover.fillFormTest.tests;

import school.redrover.fillFormTest.data.TestData;
import school.redrover.fillFormTest.pages.DemoqaFormPage;
import org.testng.annotations.Test;

public class DemoQaFormTest extends BaseTest {
    DemoqaFormPage demoqaFormPage = new DemoqaFormPage();
    TestData data = new TestData();
    private final static String DEMO_QA_PRACTICE_FORM_URL = "/automation-practice-form",

    // Label in table (key)
            STUDENT_NAME = "Student Name",
            STUDENT_EMAIL = "Student Email",
            STUDENT_GENDER = "Gender",
            STUDENT_MOBILE = "Mobile",
            STUDENT_BIRTH = "Date of Birth",
            STUDENT_SUBJECTS = "Subjects",
            STUDENT_HOBBIES = "Hobbies",
            STUDENT_ADDRESS = "Address",
            STUDENT_STATE_AND_CITY = "State and City";

    @Test(description = "Заполнение всех полей в таблице и проверка их отображения в форме")
    void testEnteringCorrectDataIntoTheForm() {
        demoqaFormPage.openPage(DEMO_QA_PRACTICE_FORM_URL);
        demoqaFormPage
                .addUserFirstName(data.firstName)
                .addUserLastName(data.lastName)
                .addUserEmail(data.email)
                .checkUserGender(data.gender)
                .addUserPhone(data.phoneNumber)
                .setUserBirth(data.yearBirth, data.month, data.dayOfBirth)
                .selectSubjects(data.subjects)
                .selectHobbies(data.hobbies)
                .addCurrentAddress(data.currentAddress)
                .choiceStateAndCity(data.state, data.city)
                .sendForm();
        demoqaFormPage
                .checkSubmittingForm(STUDENT_NAME, data.firstName + " " + data.lastName)
                .checkSubmittingForm(STUDENT_EMAIL, data.email)
                .checkSubmittingForm(STUDENT_GENDER, data.gender)
                .checkSubmittingForm(STUDENT_MOBILE, data.phoneNumber)
                .checkSubmittingForm(STUDENT_BIRTH, data.dayOfBirth + " " + data.month + "," + data.yearBirth)
                .checkSubmittingForm(STUDENT_SUBJECTS, data.subjects)
                .checkSubmittingForm(STUDENT_HOBBIES, data.hobbies)
                .checkSubmittingForm(STUDENT_ADDRESS, data.currentAddress)
                .checkSubmittingForm(STUDENT_STATE_AND_CITY, data.state + " " + data.city);
    }

    @Test(description = "Заполнение обязательных полей в таблице и проверка их отображения в форме")
    void testEnteringMinimumDataIntoTheForm() {
        demoqaFormPage.openPage(DEMO_QA_PRACTICE_FORM_URL);
        demoqaFormPage
                .addUserFirstName(data.firstName)
                .addUserLastName(data.lastName)
                .checkUserGender(data.gender)
                .addUserPhone(data.phoneNumber)
                .sendForm();
        demoqaFormPage
                .checkSubmittingForm(STUDENT_NAME, data.firstName + " " + data.lastName)
                .checkSubmittingForm(STUDENT_GENDER, data.gender)
                .checkSubmittingForm(STUDENT_MOBILE, data.phoneNumber);
    }
}
