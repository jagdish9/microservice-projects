package com.appservice.effectiverestapi.record;

import com.appservice.effectiverestapi.entity.Employee;

public record EmployeeResult(Employee emp, boolean isCreated) {
}
