SUMMARY = "Ralink 8189es amlogic v1.0"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://ifcfg-wlan0;md5=a84acae65af4b2d44d5035aa9f63cd85"

inherit module

SRC_URI = "https://github.com/khadas/android_hardware_wifi_realtek_drivers_8189es/archive/f971e4b.tar.gz"

SRC_URI[md5sum] = "a31dad81674e76f4def8cfc897aec308"
SRC_URI[sha256sum] = "28027263740ffbbb4b6895f0a469e52081a8fab0e855d57ed0178eb98cc6c24a"


EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR} KSRC=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/android_hardware_wifi_realtek_drivers_8189es-f971e4b/rtl8189ES/"

do_configure_prepend(){
      sed -i 's/-DCONFIG_CONCURRENT_MODE//g; s/^CONFIG_POWER_SAVING.*$/CONFIG_POWER_SAVING = n/g; s/^CONFIG_RTW_DEBUG.*/CONFIG_RTW_DEBUG = n/g' ${S}Makefile
      sed -i 's/^#define CONFIG_DEBUG.*//g' ${S}/include/autoconf.h
      sed -i 's/#define DEFAULT_RANDOM_MACADDR.*1/#define DEFAULT_RANDOM_MACADDR 0/g' ${S}/core/rtw_ieee80211.c

}
do_compile () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
    oe_runmake 'M={D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/amlogic/wifi' \
        'KERNEL_SOURCE=${STAGING_KERNEL_DIR}' \
        'LINUX_SRC=${STAGING_KERNEL_DIR}' \
        'KDIR=${STAGING_KERNEL_DIR}' \
        'KERNDIR=${STAGING_KERNEL_DIR}' \
        'KSRC=${STAGING_KERNEL_DIR}' \
        'KERNEL_VERSION=${KERNEL_VERSION}' \
        'KVER=${KERNEL_VERSION}' \
        'CC=${KERNEL_CC}' \
        'AR=${KERNEL_AR}' \
        'LD=${KERNEL_LD}' \
        'USER_EXTRA_CFLAGS="-fgnu89-inline"'
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/amlogic/wifi
    install -m 0644 ${S}/8189es.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/amlogic/wifi
}
