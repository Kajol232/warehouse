package com.muhammad.warehouse.config.constant;

public class Authority {
    public static final String[] WORKER_USER_AUTHORITIES = { "users:read", "stocks:create", "orders:create", "orders:read"};
    public static final String[] ADMIN_AUTHORITIES = { "users:read", "users:create", "users:update", "users:delete",
            "stocks:create", "stocks:read", "stocks:update", "stocks:delete", "orders:create", "orders:read",
            "orders:update", "orders:delete"};
}
