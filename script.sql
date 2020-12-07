CREATE DATABASE IF NOT EXISTS `hms` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `hms`;

CREATE TABLE `booking` (
                           `id` int(11) NOT NULL,
                           `arrival` date DEFAULT NULL,
                           `departure` date DEFAULT NULL,
                           `total` decimal(19,2) DEFAULT NULL,
                           `guest_id` int(11) DEFAULT NULL,
                           `room_id` int(11) DEFAULT NULL,
                           `staff_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `checks` (
                          `id` int(11) NOT NULL,
                          `checkTime` datetime DEFAULT NULL,
                          `status` bit(1) DEFAULT NULL,
                          `booking_id` int(11) DEFAULT NULL,
                          `staff_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `guest` (
                         `id` int(11) NOT NULL,
                         `birthDate` date DEFAULT NULL,
                         `document` varchar(255) DEFAULT NULL,
                         `email` varchar(255) DEFAULT NULL,
                         `name` varchar(255) DEFAULT NULL,
                         `phoneNumber` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `payment` (
                           `id` int(11) NOT NULL,
                           `payTime` datetime DEFAULT NULL,
                           `paymentMethod` varchar(255) DEFAULT NULL,
                           `value` decimal(19,2) DEFAULT NULL,
                           `booking_id` int(11) DEFAULT NULL,
                           `staff_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `room` (
                        `id` int(11) NOT NULL,
                        `number` int(11) DEFAULT NULL,
                        `roomType_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `roomtype` (
                            `id` int(11) NOT NULL,
                            `dailyPrice` decimal(19,2) DEFAULT NULL,
                            `description` varchar(255) DEFAULT NULL,
                            `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `staff` (
                         `id` int(11) NOT NULL,
                         `accessLevel` varchar(255) DEFAULT NULL,
                         `login` varchar(255) DEFAULT NULL,
                         `name` varchar(255) DEFAULT NULL,
                         `password` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


ALTER TABLE `booking`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FKs0yg8nbvp96u5vf3wfh685rcp` (`guest_id`),
    ADD KEY `FKowymy55vrygpdnacvnbck2js3` (`room_id`),
    ADD KEY `FKfvwnge63uejojl97650lpyd02` (`staff_id`);

ALTER TABLE `checks`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FKmeomx1m6ycre055o90qdksoyw` (`booking_id`),
    ADD KEY `FKaqc6lyqmy0mv6rwtnuodu2a6n` (`staff_id`);

ALTER TABLE `guest`
    ADD PRIMARY KEY (`id`);

ALTER TABLE `payment`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FKt30qv3axmqwhk1wag867yxqum` (`booking_id`),
    ADD KEY `FKov0p9jd34njt27gu7jks7mv4i` (`staff_id`);

ALTER TABLE `room`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FKklafyw0cu1h3eo2m7vl1f4ga1` (`roomType_id`);

ALTER TABLE `roomtype`
    ADD PRIMARY KEY (`id`);

ALTER TABLE `staff`
    ADD PRIMARY KEY (`id`);


ALTER TABLE `booking`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `checks`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `guest`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `payment`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `room`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `roomtype`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `staff`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

INSERT INTO `staff`(accessLevel, login, name, password)
VALUES ('OWNER', 'admin', 'ADMIN - MUST BE DELETED OR EDITED', 'admin');
COMMIT;