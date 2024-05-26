import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import model.LearningGroup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadFilesTests {
    ClassLoader classLoader = ReadFilesTests.class.getClassLoader();

    @Test
    void pdfFileFromZipTest() throws Exception {
        try (InputStream inputStream = classLoader.getResourceAsStream("Архив.zip");
             ZipInputStream zipInputStream = new ZipInputStream(inputStream);
             ZipFile zipFile = new ZipFile(new File("src/test/resources/Архив.zip"))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals("Архив/file.pdf")) {
                    try (InputStream pdfInputStream = zipFile.getInputStream(entry)) {
                        PDF pdf = new PDF(pdfInputStream);
                        assertThat(pdf.text).isEqualTo("Это pdf-файл!\n");
                    }
                }
            }
        }
    }

    @Test
    void xlsxFileFromZipTest() throws Exception {
        try (InputStream inputStream = classLoader.getResourceAsStream("Архив.zip");
             ZipInputStream zipInputStream = new ZipInputStream(inputStream);
             ZipFile zipFile = new ZipFile(new File("src/test/resources/Архив.zip"))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals("Архив/file.xlsx")) {
                    try (InputStream fileInputStream = zipFile.getInputStream(entry)) {
                        XLS xlsx = new XLS(fileInputStream);
                        String actual = xlsx.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue();
                        assertThat(actual).isEqualTo("Это xlsx-файл!");
                    }
                }
            }
        }
    }

    @Test
    void csvFileFromZipTest() throws Exception {
        try (InputStream inputStream = classLoader.getResourceAsStream("Архив.zip");
             ZipInputStream zipInputStream = new ZipInputStream(inputStream);
             ZipFile zipFile = new ZipFile(new File("src/test/resources/Архив.zip"))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals("Архив/file.csv")) {
                    try (InputStream fileInputStream = zipFile.getInputStream(entry);
                         CSVReader reader = new CSVReader(new InputStreamReader(fileInputStream))) {
                        List<String[]> data = reader.readAll();
                        assertThat(data.size()).isEqualTo(4);
                        assertThat(data.get(1)).isEqualTo(new String[]{"Сидел", "Петух", "На лавочке"});
                        assertThat(data.get(2)).isEqualTo(new String[]{"Считал", "Свои", "Булавочки"});
                        assertThat(data.get(3)).isEqualTo(new String[]{"", "Это csv-файл!", ""});
                    }
                }
            }
        }
    }

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
