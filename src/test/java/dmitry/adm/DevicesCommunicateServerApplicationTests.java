package dmitry.adm;

import dmitry.adm.entity.dto.DeviceStats;
import dmitry.adm.entity.model.DeviceValue;
import dmitry.adm.service.impl.DeviceServiceImpl;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class DevicesCommunicateServerApplicationTests {

    private List<DeviceStats> groupValuesByHours(List<DeviceValue> values, int totalHours, LocalDateTime startTime) {


        Map<LocalDateTime, ValueAndCount> groupedValues = new TreeMap<>();

        LocalDateTime localDateTime = LocalDateTime.of(
                startTime.toLocalDate(),
                LocalTime.of(
                        startTime.getHour(),
                        0
                )
        );

        for (int i = 0; i < totalHours; i++) {
            localDateTime = localDateTime.plusHours(1);

            groupedValues.put(localDateTime, null);
        }


        for (DeviceValue deviceValue : values) {

            LocalDateTime timeReceive = deviceValue.getTimeReceive();

            LocalDateTime ofLastHourTime = LocalDateTime.of(
                    timeReceive.toLocalDate(),
                    LocalTime.of(
                            timeReceive.getHour(),
                            0
                    )
            );

            ofLastHourTime = ofLastHourTime.plusHours(1);

            if (!groupedValues.containsKey(ofLastHourTime)) {
                continue;
            }

            var valueAndCount = groupedValues.get(ofLastHourTime);
            if (valueAndCount == null) {
                groupedValues.put(ofLastHourTime, new ValueAndCount(deviceValue.getValue(), 1));
            } else {
                valueAndCount.value += deviceValue.getValue();
                valueAndCount.count++;
            }

        }


        return groupedValues.entrySet().stream()
                .map(record -> {
                    var valueAndCount = record.getValue();

                    return new DeviceStats(
                            record.getKey(), valueAndCount == null ? null : valueAndCount.average()
                    );
                })
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    private static class ValueAndCount {

        int value;
        int count;

        int average() {
            return value / count;
        }

    }

    @Test
    void testStats() {

        int totalHours = 20;
        List<DeviceStats> deviceStats = groupValuesByHours(
                List.of(
                        new DeviceValue(
                                1,

                                LocalDateTime.of(
                                        2023, Month.MAY, 31,
                                        10, 15
                                ),
                                19
                        ),

                        new DeviceValue(
                                1,

                                LocalDateTime.of(
                                        2023, Month.MAY, 31,
                                        10, 30
                                ),
                                25
                        ),

                        new DeviceValue(
                                1,

                                LocalDateTime.of(
                                        2023, Month.MAY, 31,
                                        10, 50
                                ),
                                19
                        ),


                        new DeviceValue(
                                1,

                                LocalDateTime.of(
                                        2023, Month.MAY, 31,
                                        19, 30
                                ),
                                30
                        ),


                        new DeviceValue(
                                1,

                                LocalDateTime.of(
                                        2023, Month.MAY, 31,
                                        21, 40
                                ),
                                25
                        ),

                        new DeviceValue(
                                1,

                                LocalDateTime.of(
                                        2023, Month.MAY, 31,
                                        21, 59
                                ),
                                30
                        )


                ), totalHours, LocalDateTime.of(
                        2023, Month.MAY, 31,
                        10, 00
                )
        );
        deviceStats.forEach(s -> System.out.println(s.getTime().format(
                DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm")) + " -> " + s.getValue())
        );

        Assertions.assertEquals(deviceStats.size(), totalHours);



    }

}
