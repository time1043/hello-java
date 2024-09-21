/**
 * 用户类型
 */
export type UserType = {
    id: number,
    username?: string,
    userAccount?: string,
    avatarUrl?: string,
    gender?: number | string,
    phone?: string,
    email?: string,
    userStatus?: number | string,
    createTime?: Date,
    userRole?: number | string,
    planetCode?: string,
    tags?: string[],
    profile?: string,
};
