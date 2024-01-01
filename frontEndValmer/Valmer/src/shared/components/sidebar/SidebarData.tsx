export const SidebarData = [
    {
        label: "Deuda",
        icon: "fa fa-money-bill",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        isOpen:false,
        subMenu: [
            {
                label: "Gubernamental",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                isOpen:false,
                subMenu: [
                    {
                        label: "Instrumentos",
                        path: "/deuda/gubernamental/instrumentos",
                        isOpen:false,
                    }
                ]
            },
            {
                label: "Corp-Banc",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                isOpen:false,
                subMenu: [
                    {
                        label: "Instrumentos",
                        path: "/deuda/corpBanc/instrumentos",
                        isOpen:false,
                    }
                ],
            },
            {
                label: "Tasas",
                path: "/deuda/tasas",
                isOpen:false,
            },
            {
                label: "Subastas",
                path: "/deuda/subastas",
                isOpen:false,
            },
            {
                label: "Subastas Flotantes",
                path: "/deuda/subastas/flotantes",
                isOpen:false,
            }
        ],
    },
    {
        label: "Internacional",
        icon: "fa fa-earth-americas",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        isOpen:false,
        subMenu: [
            {
                label: "Instrumentos",
                path: "/internacional/instrumentos",
                isOpen:false,
            },
            {
                label: "Proceso Eurobonos",
                path: "/internacional/eurobonos",
                isOpen:false,
            },
            {
                label: "Lipper Fund",
                path: "/internacional/lipper/fund",
                isOpen:false,
            }
        ],
    },
    {
        label: "Derivados",
        icon: "fa fa-cubes",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        isOpen:false,
        subMenu: [
            {
                label: "Listados",
                path: "/derivados/listados",
                isOpen:false,
            },
            {
                label: "Teóricos",
                path: "/derivados/teoricos",
                isOpen:false,
            },
            {
                label: "Chicago",
                path: "/derivados/chicago",
                isOpen:false,
            },
            {
                label: "Def Derivados",
                path: "/derivados/definicion/derivados",
                isOpen:false,
            }
        ],
    },
    {
        label: "Acciones",
        icon: "fa-brands fa-codepen",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        isOpen:false,
        subMenu: [
            {
                label: "Instrumentos",
                path:"/acciones/instrumentos",
                isOpen:false,
            },
            {
                label: "Proceso RV",
                path:"/acciones/procesos/rv",
                isOpen:false,
            }
        ],
    },
    {
        label: "Calificaciones",
        icon: "fa fa-shield-halved",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        isOpen:false,
        subMenu: [
            {
                label: "Emisoras",
                path:"/calificaciones/emisoras",
                isOpen:false,
            },
            {
                label: "Instrumentos",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                isOpen:false,
                subMenu: [
                    {
                        label: "General",
                        path: "/calificaciones/instrumentos/general",
                        isOpen:false,
                    },
                    {
                        label: "Fondos",
                        path: "/calificaciones/instrumentos/fondos",
                        isOpen:false,
                    },
                ],
            },
            {
                label: "Programas",
                path:"/calificaciones/programas",
                isOpen:false,
            }
        ],
    },
    {
        label: "LATAM",
        icon: "fa fa-building-columns",
        iconClose: "fa fa-angle-down",
        iconOpen: "fa fa-angle-up",
        isOpen:false,
        subMenu: [
            {
                label: "Panamá",
                path: "/latam/panama/panama",
                isOpen:false,
            },
            {
                label: "Costa Rica",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                isOpen:false,
                subMenu: [
                    {
                        label: "Costa Rica",
                        path: "/latam/costa_rica/costa_rica",
                        isOpen:false,
                    },
                    {
                        label: "CAU",
                        path: "/latam/costa_rica/cau",
                        isOpen:false,
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
        isOpen:false,
        subMenu: [
            {
                label: "Catálogos",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                isOpen:false,
                subMenu: [
                    {
                        label: "Catálogos México",
                        path:"/admin/catalogos/mexico",
                        isOpen:false,
                    },
                    {
                        label: "Catálogos Calificaciones",
                        path:"/admin/catalogos/calificaciones",
                        isOpen:false,
                    },
                    {
                        label: "Catálogos Panamá",
                        path:"/admin/catalogos/panama",
                        isOpen:false,
                    },
                    {
                        label: "Catálogos CR",
                        path:"/admin/catalogos/costa/rica",
                        isOpen:false,
                    },
                    {
                        label: "Catálogos CAU",
                        path:"/admin/catalogos/cau",
                        isOpen:false,
                    },
                    {
                        label: "Catálogos Archivo",
                        path:"/admin/catalogos/archivos",
                        isOpen:false,
                    },
                    {
                        label: "Catálogos Brókers",
                        path:"/admin/catalogos/brokers",
                        isOpen:false,
                    },
                    {
                        label: "Catálogos Perfil",
                        path:"/admin/catalogos/perfil",
                        isOpen:false,
                    },
                    {
                        label: "Catálogos Base Acc",
                        path:"/admin/catalogos/acc",
                        isOpen:false,
                    },
                    {
                        label: "Catálogos Class Sec",
                        path:"/admin/catalogos/sec",
                        isOpen:false,
                    },
                ],
            },
            {
                label: "Usuarios",
                path:"/admin/usuarios",
                isOpen:false,
            },
            {
                label: "Mail Clientes",
                path:"/admin/mail/clientes",
                isOpen:false,
            },
            {
                label: "Admin User Web",
                path:"/admin/user/web",
                isOpen:false,
            },
            {
                label: "Reuters",
                iconClose: "fa fa-angle-down",
                iconOpen: "fa fa-angle-up",
                isOpen:false,
                subMenu: [
                    {
                        label: "Reuters UMS Mex Cat",
                        path:"/admin/reuters/ums/mex/cat",
                        isOpen:false,
                    },
                    {
                        label: "Reuters UMS Latam",
                        path:"/admin/reuters/ums/latam",
                        isOpen:false,
                    },
                    {
                        label: "Reuters Liq Latam Cat",
                        path:"/admin/reuters/liq/latam/cat",
                        isOpen:false,
                    },
                ],
            }
        ],
    }
];