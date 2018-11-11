import Main from '@/views/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: 'Login - 登录'
    },
    component: () => import('@/views/login.vue')
};

export const page404 = {
    path: '/*',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: () => import('@/views/error-page/404.vue')
};

export const page403 = {
    path: '/403',
    meta: {
        title: '403-权限不足'
    },
    name: 'error-403',
    component: () => import('@//views/error-page/403.vue')
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error-500',
    component: () => import('@/views/error-page/500.vue')
};

export const preview = {
    path: '/preview',
    name: 'preview',
    component: () => import('@/views/form/article-publish/preview.vue')
};

export const locking = {
    path: '/locking',
    name: 'locking',
    component: () => import('@/views/main-components/lockscreen/components/locking-page.vue')
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/home',
    component: Main,
    children: [
        { path: 'home', title: {i18n: 'home'}, name: 'home_index', component: () => import('@/views/home/home.vue') },
        { path: 'ownspace', title: '个人中心', name: 'ownspace_index', component: () => import('@/views/own-space/own-space.vue') },
       // { path: 'order/:order_id', title: '订单详情', name: 'order-info', component: () => import('@/views/advanced-router/component/order-info.vue') }, // 用于展示动态路由
       // { path: 'shopping', title: '购物详情', name: 'shopping', component: () => import('@/views/advanced-router/component/shopping-info.vue') }, // 用于展示带参路由
        { path: 'message', title: '消息中心', name: 'message_index', component: () => import('@/views/message/message.vue') }
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [
    {
        path: '/stat',
        icon: 'social-buffer',
        name: 'stat',
        title: '综合统计',
        component: Main,
        children: [
            {
                path: 'inst-stat',
                icon: 'social-buffer',
                name: 'inst-stat',
                title: '单位信息统计',
                component: () => import('@/views/stat/inst-stat.vue')
            },
            {
                path: 'person-stat',
                icon: 'social-buffer',
                name: 'person-stat',
                title: '人员信息统计',
                component: () => import('@/views/stat/person-stat.vue')
            },
            // {
            //     path: 'contract-stat',
            //     icon: 'social-buffer',
            //     name: 'contract-stat',
            //     title: '合同信息统计',
            //     component: () => import('@/views/stat/contract-stat.vue')
            // },
            {
                path: 'group-stat',
                icon: 'social-buffer',
                name: 'group-stat',
                title: '团队信息统计',
                component: () => import('@/views/stat/team-stat.vue')
            }
        ]
    },
    {
        path: '/contract',
        icon: 'social-buffer',
        name: 'contract',
        title: '合同管理',
        component: Main,
        children: [
            {
                path: 'contract-index',
                icon: 'social-buffer',
                name: 'contract-index',
                title: '合同管理',
                component: () => import('@/views/contract/contract-index.vue')
            }
        ]
    },
    {
        path: '/person',
        icon: 'social-buffer',
        name: 'person',
        title: '人员管理',
        component: Main,
        children: [
            {
                path: 'person-index',
                icon: 'social-buffer',
                name: 'person-index',
                title: '人员管理',
                component: () => import('@/views/person/person-index.vue')
            },
            {
                path: 'prsn-ability-index',
                icon: 'social-buffer',
                name: 'prsn-ability-index',
                title: '人员能力管理',
                component: () => import('@/views/prsn-ability/prsn-ability-index.vue')
            },
        ]
    },
    {
        path: '/team',
        icon: 'social-buffer',
        name: 'team',
        title: '团队管理',
        component: Main,
        children: [
            {
                path: 'team-index',
                icon: 'social-buffer',
                name: 'team-index',
                title: '团队管理',
                component: () => import('@/views/team/team-index.vue')
            }
        ]
    },
    {
        path: '/institute',
        icon: 'social-buffer',
        name: 'institute',
        title: '单位管理',
        component: Main,
        children: [
            {
                path: 'institute-index',
                icon: 'social-buffer',
                name: 'institute-index',
                title: '单位管理',
                component: () => import('@/views/institute/institute-index.vue')
            }
        ]
    },
    {
        path: '/doc',
        icon: 'social-buffer',
        name: 'doc',
        title: '文件管理',
        component: Main,
        children: [
            {
                path: 'doc-index',
                icon: 'social-buffer',
                name: 'doc-index',
                title: '文件管理',
                component: () => import('@/views/doc/doc-index.vue')
            },
            {
                path: 'data-index',
                icon: 'social-buffer',
                name: 'data-index',
                title: '数据导入',
                component: () => import('@/views/doc/data-index.vue')
            },
            /*
            {
                path: 'doc-upload',
                icon: 'social-buffer',
                name: 'doc-upload',
                title: '文件管理',
                component: () => import('@/views/my-components/file-upload/file-upload.vue')
            },
            */
        ]
    },
    {
        path: '/achiv',
        icon: 'social-buffer',
        name: 'achiv',
        title: '成果管理',
        component: Main,
        children: [
            {
                path: 'achiv-index',
                icon: 'social-buffer',
                name: 'achiv-index',
                title: '成果管理',
                component: () => import('@/views/achiv/achiv-index.vue')
            }
        ]
    },
    {
        path: '/sys',
        icon: 'social-buffer',
        name: 'sys',
        title: '系统管理',
        component: Main,
        children: [
            {
                path: 'dict-index',
                icon: 'social-buffer',
                name: 'dict-index',
                title: '字典管理',
                component: () => import('@/views/dict/dict-index.vue')
            }
        ]
    }
];

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    otherRouter,
    preview,
    locking,
    ...appRouter,
    page500,
    page403,
    page404
];
