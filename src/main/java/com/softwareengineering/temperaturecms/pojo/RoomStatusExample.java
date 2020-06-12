package com.softwareengineering.temperaturecms.pojo;

import java.util.ArrayList;
import java.util.List;

public class RoomStatusExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RoomStatusExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRoomIdIsNull() {
            addCriterion("room_id is null");
            return (Criteria) this;
        }

        public Criteria andRoomIdIsNotNull() {
            addCriterion("room_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoomIdEqualTo(Long value) {
            addCriterion("room_id =", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotEqualTo(Long value) {
            addCriterion("room_id <>", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdGreaterThan(Long value) {
            addCriterion("room_id >", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdGreaterThanOrEqualTo(Long value) {
            addCriterion("room_id >=", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdLessThan(Long value) {
            addCriterion("room_id <", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdLessThanOrEqualTo(Long value) {
            addCriterion("room_id <=", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdIn(List<Long> values) {
            addCriterion("room_id in", values, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotIn(List<Long> values) {
            addCriterion("room_id not in", values, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdBetween(Long value1, Long value2) {
            addCriterion("room_id between", value1, value2, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotBetween(Long value1, Long value2) {
            addCriterion("room_id not between", value1, value2, "roomId");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureIsNull() {
            addCriterion("current_temperature is null");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureIsNotNull() {
            addCriterion("current_temperature is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureEqualTo(Double value) {
            addCriterion("current_temperature =", value, "currentTemperature");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureNotEqualTo(Double value) {
            addCriterion("current_temperature <>", value, "currentTemperature");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureGreaterThan(Double value) {
            addCriterion("current_temperature >", value, "currentTemperature");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureGreaterThanOrEqualTo(Double value) {
            addCriterion("current_temperature >=", value, "currentTemperature");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureLessThan(Double value) {
            addCriterion("current_temperature <", value, "currentTemperature");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureLessThanOrEqualTo(Double value) {
            addCriterion("current_temperature <=", value, "currentTemperature");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureIn(List<Double> values) {
            addCriterion("current_temperature in", values, "currentTemperature");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureNotIn(List<Double> values) {
            addCriterion("current_temperature not in", values, "currentTemperature");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureBetween(Double value1, Double value2) {
            addCriterion("current_temperature between", value1, value2, "currentTemperature");
            return (Criteria) this;
        }

        public Criteria andCurrentTemperatureNotBetween(Double value1, Double value2) {
            addCriterion("current_temperature not between", value1, value2, "currentTemperature");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureIsNull() {
            addCriterion("target_temperature is null");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureIsNotNull() {
            addCriterion("target_temperature is not null");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureEqualTo(Double value) {
            addCriterion("target_temperature =", value, "targetTemperature");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureNotEqualTo(Double value) {
            addCriterion("target_temperature <>", value, "targetTemperature");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureGreaterThan(Double value) {
            addCriterion("target_temperature >", value, "targetTemperature");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureGreaterThanOrEqualTo(Double value) {
            addCriterion("target_temperature >=", value, "targetTemperature");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureLessThan(Double value) {
            addCriterion("target_temperature <", value, "targetTemperature");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureLessThanOrEqualTo(Double value) {
            addCriterion("target_temperature <=", value, "targetTemperature");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureIn(List<Double> values) {
            addCriterion("target_temperature in", values, "targetTemperature");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureNotIn(List<Double> values) {
            addCriterion("target_temperature not in", values, "targetTemperature");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureBetween(Double value1, Double value2) {
            addCriterion("target_temperature between", value1, value2, "targetTemperature");
            return (Criteria) this;
        }

        public Criteria andTargetTemperatureNotBetween(Double value1, Double value2) {
            addCriterion("target_temperature not between", value1, value2, "targetTemperature");
            return (Criteria) this;
        }

        public Criteria andFansSpeedIsNull() {
            addCriterion("fans_speed is null");
            return (Criteria) this;
        }

        public Criteria andFansSpeedIsNotNull() {
            addCriterion("fans_speed is not null");
            return (Criteria) this;
        }

        public Criteria andFansSpeedEqualTo(Double value) {
            addCriterion("fans_speed =", value, "fansSpeed");
            return (Criteria) this;
        }

        public Criteria andFansSpeedNotEqualTo(Double value) {
            addCriterion("fans_speed <>", value, "fansSpeed");
            return (Criteria) this;
        }

        public Criteria andFansSpeedGreaterThan(Double value) {
            addCriterion("fans_speed >", value, "fansSpeed");
            return (Criteria) this;
        }

        public Criteria andFansSpeedGreaterThanOrEqualTo(Double value) {
            addCriterion("fans_speed >=", value, "fansSpeed");
            return (Criteria) this;
        }

        public Criteria andFansSpeedLessThan(Double value) {
            addCriterion("fans_speed <", value, "fansSpeed");
            return (Criteria) this;
        }

        public Criteria andFansSpeedLessThanOrEqualTo(Double value) {
            addCriterion("fans_speed <=", value, "fansSpeed");
            return (Criteria) this;
        }

        public Criteria andFansSpeedIn(List<Double> values) {
            addCriterion("fans_speed in", values, "fansSpeed");
            return (Criteria) this;
        }

        public Criteria andFansSpeedNotIn(List<Double> values) {
            addCriterion("fans_speed not in", values, "fansSpeed");
            return (Criteria) this;
        }

        public Criteria andFansSpeedBetween(Double value1, Double value2) {
            addCriterion("fans_speed between", value1, value2, "fansSpeed");
            return (Criteria) this;
        }

        public Criteria andFansSpeedNotBetween(Double value1, Double value2) {
            addCriterion("fans_speed not between", value1, value2, "fansSpeed");
            return (Criteria) this;
        }

        public Criteria andFareRateIsNull() {
            addCriterion("fare_rate is null");
            return (Criteria) this;
        }

        public Criteria andFareRateIsNotNull() {
            addCriterion("fare_rate is not null");
            return (Criteria) this;
        }

        public Criteria andFareRateEqualTo(Double value) {
            addCriterion("fare_rate =", value, "fareRate");
            return (Criteria) this;
        }

        public Criteria andFareRateNotEqualTo(Double value) {
            addCriterion("fare_rate <>", value, "fareRate");
            return (Criteria) this;
        }

        public Criteria andFareRateGreaterThan(Double value) {
            addCriterion("fare_rate >", value, "fareRate");
            return (Criteria) this;
        }

        public Criteria andFareRateGreaterThanOrEqualTo(Double value) {
            addCriterion("fare_rate >=", value, "fareRate");
            return (Criteria) this;
        }

        public Criteria andFareRateLessThan(Double value) {
            addCriterion("fare_rate <", value, "fareRate");
            return (Criteria) this;
        }

        public Criteria andFareRateLessThanOrEqualTo(Double value) {
            addCriterion("fare_rate <=", value, "fareRate");
            return (Criteria) this;
        }

        public Criteria andFareRateIn(List<Double> values) {
            addCriterion("fare_rate in", values, "fareRate");
            return (Criteria) this;
        }

        public Criteria andFareRateNotIn(List<Double> values) {
            addCriterion("fare_rate not in", values, "fareRate");
            return (Criteria) this;
        }

        public Criteria andFareRateBetween(Double value1, Double value2) {
            addCriterion("fare_rate between", value1, value2, "fareRate");
            return (Criteria) this;
        }

        public Criteria andFareRateNotBetween(Double value1, Double value2) {
            addCriterion("fare_rate not between", value1, value2, "fareRate");
            return (Criteria) this;
        }

        public Criteria andStartUpIsNull() {
            addCriterion("start_up is null");
            return (Criteria) this;
        }

        public Criteria andStartUpIsNotNull() {
            addCriterion("start_up is not null");
            return (Criteria) this;
        }

        public Criteria andStartUpEqualTo(Long value) {
            addCriterion("start_up =", value, "startUp");
            return (Criteria) this;
        }

        public Criteria andStartUpNotEqualTo(Long value) {
            addCriterion("start_up <>", value, "startUp");
            return (Criteria) this;
        }

        public Criteria andStartUpGreaterThan(Long value) {
            addCriterion("start_up >", value, "startUp");
            return (Criteria) this;
        }

        public Criteria andStartUpGreaterThanOrEqualTo(Long value) {
            addCriterion("start_up >=", value, "startUp");
            return (Criteria) this;
        }

        public Criteria andStartUpLessThan(Long value) {
            addCriterion("start_up <", value, "startUp");
            return (Criteria) this;
        }

        public Criteria andStartUpLessThanOrEqualTo(Long value) {
            addCriterion("start_up <=", value, "startUp");
            return (Criteria) this;
        }

        public Criteria andStartUpIn(List<Long> values) {
            addCriterion("start_up in", values, "startUp");
            return (Criteria) this;
        }

        public Criteria andStartUpNotIn(List<Long> values) {
            addCriterion("start_up not in", values, "startUp");
            return (Criteria) this;
        }

        public Criteria andStartUpBetween(Long value1, Long value2) {
            addCriterion("start_up between", value1, value2, "startUp");
            return (Criteria) this;
        }

        public Criteria andStartUpNotBetween(Long value1, Long value2) {
            addCriterion("start_up not between", value1, value2, "startUp");
            return (Criteria) this;
        }

        public Criteria andModeIsNull() {
            addCriterion("mode is null");
            return (Criteria) this;
        }

        public Criteria andModeIsNotNull() {
            addCriterion("mode is not null");
            return (Criteria) this;
        }

        public Criteria andModeEqualTo(Integer value) {
            addCriterion("mode =", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotEqualTo(Integer value) {
            addCriterion("mode <>", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeGreaterThan(Integer value) {
            addCriterion("mode >", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeGreaterThanOrEqualTo(Integer value) {
            addCriterion("mode >=", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLessThan(Integer value) {
            addCriterion("mode <", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLessThanOrEqualTo(Integer value) {
            addCriterion("mode <=", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeIn(List<Integer> values) {
            addCriterion("mode in", values, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotIn(List<Integer> values) {
            addCriterion("mode not in", values, "mode");
            return (Criteria) this;
        }

        public Criteria andModeBetween(Integer value1, Integer value2) {
            addCriterion("mode between", value1, value2, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotBetween(Integer value1, Integer value2) {
            addCriterion("mode not between", value1, value2, "mode");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Long value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Long value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Long value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Long value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Long value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Long> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Long> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Long value1, Long value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Long value1, Long value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeIsNull() {
            addCriterion("on_off_time is null");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeIsNotNull() {
            addCriterion("on_off_time is not null");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeEqualTo(Integer value) {
            addCriterion("on_off_time =", value, "onOffTime");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeNotEqualTo(Integer value) {
            addCriterion("on_off_time <>", value, "onOffTime");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeGreaterThan(Integer value) {
            addCriterion("on_off_time >", value, "onOffTime");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("on_off_time >=", value, "onOffTime");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeLessThan(Integer value) {
            addCriterion("on_off_time <", value, "onOffTime");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeLessThanOrEqualTo(Integer value) {
            addCriterion("on_off_time <=", value, "onOffTime");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeIn(List<Integer> values) {
            addCriterion("on_off_time in", values, "onOffTime");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeNotIn(List<Integer> values) {
            addCriterion("on_off_time not in", values, "onOffTime");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeBetween(Integer value1, Integer value2) {
            addCriterion("on_off_time between", value1, value2, "onOffTime");
            return (Criteria) this;
        }

        public Criteria andOnOffTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("on_off_time not between", value1, value2, "onOffTime");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesIsNull() {
            addCriterion("dispatch_times is null");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesIsNotNull() {
            addCriterion("dispatch_times is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesEqualTo(Integer value) {
            addCriterion("dispatch_times =", value, "dispatchTimes");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesNotEqualTo(Integer value) {
            addCriterion("dispatch_times <>", value, "dispatchTimes");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesGreaterThan(Integer value) {
            addCriterion("dispatch_times >", value, "dispatchTimes");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_times >=", value, "dispatchTimes");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesLessThan(Integer value) {
            addCriterion("dispatch_times <", value, "dispatchTimes");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_times <=", value, "dispatchTimes");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesIn(List<Integer> values) {
            addCriterion("dispatch_times in", values, "dispatchTimes");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesNotIn(List<Integer> values) {
            addCriterion("dispatch_times not in", values, "dispatchTimes");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_times between", value1, value2, "dispatchTimes");
            return (Criteria) this;
        }

        public Criteria andDispatchTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_times not between", value1, value2, "dispatchTimes");
            return (Criteria) this;
        }

        public Criteria andRdrNumIsNull() {
            addCriterion("rdr_num is null");
            return (Criteria) this;
        }

        public Criteria andRdrNumIsNotNull() {
            addCriterion("rdr_num is not null");
            return (Criteria) this;
        }

        public Criteria andRdrNumEqualTo(Integer value) {
            addCriterion("rdr_num =", value, "rdrNum");
            return (Criteria) this;
        }

        public Criteria andRdrNumNotEqualTo(Integer value) {
            addCriterion("rdr_num <>", value, "rdrNum");
            return (Criteria) this;
        }

        public Criteria andRdrNumGreaterThan(Integer value) {
            addCriterion("rdr_num >", value, "rdrNum");
            return (Criteria) this;
        }

        public Criteria andRdrNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("rdr_num >=", value, "rdrNum");
            return (Criteria) this;
        }

        public Criteria andRdrNumLessThan(Integer value) {
            addCriterion("rdr_num <", value, "rdrNum");
            return (Criteria) this;
        }

        public Criteria andRdrNumLessThanOrEqualTo(Integer value) {
            addCriterion("rdr_num <=", value, "rdrNum");
            return (Criteria) this;
        }

        public Criteria andRdrNumIn(List<Integer> values) {
            addCriterion("rdr_num in", values, "rdrNum");
            return (Criteria) this;
        }

        public Criteria andRdrNumNotIn(List<Integer> values) {
            addCriterion("rdr_num not in", values, "rdrNum");
            return (Criteria) this;
        }

        public Criteria andRdrNumBetween(Integer value1, Integer value2) {
            addCriterion("rdr_num between", value1, value2, "rdrNum");
            return (Criteria) this;
        }

        public Criteria andRdrNumNotBetween(Integer value1, Integer value2) {
            addCriterion("rdr_num not between", value1, value2, "rdrNum");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeIsNull() {
            addCriterion("change_temp_time is null");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeIsNotNull() {
            addCriterion("change_temp_time is not null");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeEqualTo(Integer value) {
            addCriterion("change_temp_time =", value, "changeTempTime");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeNotEqualTo(Integer value) {
            addCriterion("change_temp_time <>", value, "changeTempTime");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeGreaterThan(Integer value) {
            addCriterion("change_temp_time >", value, "changeTempTime");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("change_temp_time >=", value, "changeTempTime");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeLessThan(Integer value) {
            addCriterion("change_temp_time <", value, "changeTempTime");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeLessThanOrEqualTo(Integer value) {
            addCriterion("change_temp_time <=", value, "changeTempTime");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeIn(List<Integer> values) {
            addCriterion("change_temp_time in", values, "changeTempTime");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeNotIn(List<Integer> values) {
            addCriterion("change_temp_time not in", values, "changeTempTime");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeBetween(Integer value1, Integer value2) {
            addCriterion("change_temp_time between", value1, value2, "changeTempTime");
            return (Criteria) this;
        }

        public Criteria andChangeTempTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("change_temp_time not between", value1, value2, "changeTempTime");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeIsNull() {
            addCriterion("change_speed_time is null");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeIsNotNull() {
            addCriterion("change_speed_time is not null");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeEqualTo(Integer value) {
            addCriterion("change_speed_time =", value, "changeSpeedTime");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeNotEqualTo(Integer value) {
            addCriterion("change_speed_time <>", value, "changeSpeedTime");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeGreaterThan(Integer value) {
            addCriterion("change_speed_time >", value, "changeSpeedTime");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("change_speed_time >=", value, "changeSpeedTime");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeLessThan(Integer value) {
            addCriterion("change_speed_time <", value, "changeSpeedTime");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeLessThanOrEqualTo(Integer value) {
            addCriterion("change_speed_time <=", value, "changeSpeedTime");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeIn(List<Integer> values) {
            addCriterion("change_speed_time in", values, "changeSpeedTime");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeNotIn(List<Integer> values) {
            addCriterion("change_speed_time not in", values, "changeSpeedTime");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeBetween(Integer value1, Integer value2) {
            addCriterion("change_speed_time between", value1, value2, "changeSpeedTime");
            return (Criteria) this;
        }

        public Criteria andChangeSpeedTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("change_speed_time not between", value1, value2, "changeSpeedTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}