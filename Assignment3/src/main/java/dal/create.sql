DROP TABLE IF EXISTS `LiftRides`;
# SkierId, ResortId, SeasonId, DayId, Time, LiftId, Vertical
CREATE TABLE `LiftRides` (
     skierID  INT     NOT NULL,
     resortId INT NOT NULL,
     dayId    INT     NOT NULL,
     time     INT     NOT NULL,
     liftId   INT     NOT NULL,
     vertical INT NOT NULL,
     PRIMARY KEY (SkierId, ResortId)
);