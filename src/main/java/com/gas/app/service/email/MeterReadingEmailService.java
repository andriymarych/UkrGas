package com.gas.app.service.email;

import com.gas.app.repository.personalAccount.MeterReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeterReadingEmailService {

    private final MailService mailService;
    private final MeterReadingRepository meterReadingRepository;

    @Transactional(readOnly = true)
    @Scheduled(cron = "0 0 12 1 * ?")
    public void sendMonthlyReminderByEmail() {

        meterReadingRepository.findUserWithUntransmittedMeterReading()
                .forEach(
                        (user) -> mailService.sendEmail(user.getEmail(),
                                "Показання",
                                "meter-reading-email-template.html"));

    }


}
