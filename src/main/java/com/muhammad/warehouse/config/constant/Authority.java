package com.muhammad.warehouse.config.constant;

public class Authority {
    public static final String[] WORKER_USER_AUTHORITIES = { "user:read", "stock:create", "sales:create", "sales:read"};
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update", "user:delete",
            "stock:create", "stock:read", "stock:update", "stock:delete", "sales:create", "sales:read",
            "sales:update", "sales:delete"};
}
