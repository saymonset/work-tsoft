export const SidebarData = [
    {
        label: "Deuda",
        icon: "fa fa-money-bill",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        subMenu: [
            {
                label: "Gubernamental",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                subMenu: [
                    {
                        label: "Instrumentos",
                        path: "/deuda/gubernamental/instrumentos",
                    }
                ]
            },
            {
                label: "Corp-Banc",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                subMenu: [
                    {
                        label: "Instrumentos",
                        path: "/deuda/corpBanc/instrumentos",
                    }
                ],
            },
            {
                label: "Tasas",
                path: "/deuda/tasas",
            },
            {
                label: "Subastas",
                path: "/deuda/subastas",
            },
            {
                label: "Subastas Flotantes",
                path: "/deuda/subastas/flotantes",
            }
        ],
    },
    {
        label: "Internacional",
        icon: "fa fa-earth-americas",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        subMenu: [
            {
                label: "Instrumentos",
                path: "/internacional/instrumentos",
            },
            {
                label: "Proceso Eurobonos",
                path: "/internacional/eurobonos",
            },
            {
                label: "Lipper Fund",
                path: "/internacional/lipper/fund",
            }
        ],
    },
    {
        label: "Derivados",
        icon: "fa fa-cubes",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        subMenu: [
            {
                label: "Listados",
                path: "/derivados/listados",
            },
            {
                label: "Teóricos",
                path: "/derivados/teoricos",
            },
            {
                label: "Chicago",
                path: "/derivados/chicago",
            },
            {
                label: "Def Derivados",
                path: "/derivados/definicion/derivados",
            }
        ],
    },
    {
        label: "Acciones",
        icon: "fa-brands fa-codepen",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        subMenu: [
            {
                label: "Instrumentos",
                path:"/acciones/instrumentos",
            },
            {
                label: "Proceso RV",
                path:"/acciones/procesos/rv",
            }
        ],
    },
    {
        label: "Calificaciones",
        icon: "fa fa-shield-halved",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        subMenu: [
            {
                label: "Emisoras",
                path:"/calificaciones/emisoras",
            },
            {
                label: "Instrumentos",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                subMenu: [
                    {
                        label: "General",
                        path: "/calificaciones/instrumentos/general",
                    },
                    {
                        label: "Fondos",
                        path: "/calificaciones/instrumentos/fondos",
                    },
                ],
            },
            {
                label: "Programas",
                path:"/calificaciones/programas",
            }
        ],
    },
    {
        label: "LATAM",
        icon: "fa fa-building-columns",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        subMenu: [
            {
                label: "Panamá",
                path: "/latam/panama/panama",
            },
            {
                label: "Costa Rica",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                subMenu: [
                    {
                        label: "Costa Rica",
                        path: "/latam/costa_rica/costa_rica",
                    },
                    {
                        label: "CAU",
                        path: "/latam/costa_rica/cau",
                    },
                ],
            },
        ],
    },
    {
        label: "Admin",
        icon: "fa fa-gears",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        subMenu: [
            {
                label: "Catálogos",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                subMenu: [
                    {
                        label: "Catálogos México",
                        path:"/admin/catalogos/mexico",
                    },
                    {
                        label: "Catálogos Calificaciones",
                        path:"/admin/catalogos/calificaciones",
                    },
                    {
                        label: "Catálogos Panamá",
                        path:"/admin/catalogos/panama",
                    },
                    {
                        label: "Catálogos CR",
                        path:"/admin/catalogos/costa/rica",
                    },
                    {
                        label: "Catálogos CAU",
                        path:"/admin/catalogos/cau",
                    },
                    {
                        label: "Catálogos Archivo",
                        path:"/admin/catalogos/archivos",
                    },
                    {
                        label: "Catálogos Brókers",
                        path:"/admin/catalogos/brokers",
                    },
                    {
                        label: "Catálogos Perfil",
                        path:"/admin/catalogos/perfil",
                    },
                    {
                        label: "Catálogos Base Acc",
                        path:"/admin/catalogos/acc",
                    },
                    {
                        label: "Catálogos Class Sec",
                        path:"/admin/catalogos/sec",
                    },
                ],
            },
            {
                label: "Usuarios",
                path:"/admin/usuarios",
            },
            {
                label: "Mail Clientes",
                path:"/admin/mail/clientes",
            },
            {
                label: "Admin User Web",
                path:"/admin/user/web",
            },
            {
                label: "Reuters",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                subMenu: [
                    {
                        label: "Reuters UMS Mex Cat",
                        path:"/admin/reuters/ums/mex/cat",
                    },
                    {
                        label: "Reuters UMS Latam",
                        path:"/admin/reuters/ums/latam",
                    },
                    {
                        label: "Reuters Liq Latam Cat",
                        path:"/admin/reuters/liq/latam/cat",
                    },
                ],
            }
        ],
    }
];