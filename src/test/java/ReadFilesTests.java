import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import model.LearningGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadFilesTests {
    ClassLoader classLoader = ReadFilesTests.class.getClassLoader();

    @DisplayName("Проверка pdf-файла из zip-архива")
    @Test
    void pdfFileFromZipTest() throws Exception {
        try (InputStream inputStream = classLoader.getResourceAsStream("Архив.zip");
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals("Архив/file.pdf")) {
                        PDF pdf = new PDF(zipInputStream);
                        assertThat(pdf.text).isEqualTo("Это pdf-файл!\n");
                    }

            }
        }
    }

    @DisplayName("Проверка xlsx-файла из zip-архива")
    @Test
    void xlsxFileFromZipTest() throws Exception {
        try (InputStream inputStream = classLoader.getResourceAsStream("Архив.zip");
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals("Архив/file.xlsx")) {
                        XLS xlsx = new XLS(zipInputStream);
                        String actual = xlsx.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue();
                        assertThat(actual).isEqualTo("Это xlsx-файл!");
                }
            }
        }
    }

    @DisplayName("Проверка csv-файла из zip-архива")
    @Test
    void csvFileFromZipTest() throws Exception {
        try (InputStream inputStream = classLoader.getResourceAsStream("Архив.zip");
             ZipInputStream zipInputStream = new ZipInputStream(inputStream);
             CSVReader reader = new CSVReader(new InputStreamReader(zipInputStream))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals("Архив/file.csv")) {
                        List<String[]> data = reader.readAll();
                        assertThat(data.size()).isEqualTo(4);
                        assertThat(data.get(1)).isEqualTo(new String[]{"Сидел", "Петух", "На лавочке"});
                        assertThat(data.get(2)).isEqualTo(new String[]{"Считал", "Свои", "Булавочки"});
                        assertThat(data.get(3)).isEqualTo(new String[]{"", "Это csv-файл!", ""});
                }
            }
        }

    }

    @DisplayName("Проверка json-файла")
    @Test
    void jsonParseTest() throws Exception {
        try (InputStream inputStream = classLoader.getResourceAsStream("learningGroup.json"))
        {
            ObjectMapper mapper = new ObjectMapper();
            LearningGroup learningGroup = mapper.readValue(inputStream, LearningGroup.class);

            assertThat(learningGroup.getId().toString())
                    .isEqualTo("e58ed763-928c-4155-bee9-fdbaaadc15f3");
            assertThat(learningGroup.getName()).isEqualTo("Java-AQA 27 поток");
            assertThat(learningGroup.getTotalStudents()).isEqualTo(3);
            assertThat(learningGroup.getTotalScore()).isEqualTo(100);
            assertThat(learningGroup.isHaveGraduated()).isFalse();
            assertThat(learningGroup.getSubjects()).isEqualTo(
                    new String[] {"Selenide", "JUnit 5", "Gradle", "Java", "Allure", "Jenkins"});

            assertThat(learningGroup.getStudents().get(0).getFirstName()).isEqualTo("Jon");
            assertThat(learningGroup.getStudents().get(0).getLastName()).isEqualTo("Snow");
            assertThat(learningGroup.getStudents().get(0).getScore()).isEqualTo(60);

            assertThat(learningGroup.getStudents().get(1).getFirstName()).isEqualTo("Daenerys");
            assertThat(learningGroup.getStudents().get(1).getLastName()).isEqualTo("Targaryen");
            assertThat(learningGroup.getStudents().get(1).getScore()).isEqualTo(55);

            assertThat(learningGroup.getStudents().get(2).getFirstName()).isEqualTo("Arya");
            assertThat(learningGroup.getStudents().get(2).getLastName()).isEqualTo("Stark");
            assertThat(learningGroup.getStudents().get(2).getScore()).isEqualTo(80);
        }
    }
}
