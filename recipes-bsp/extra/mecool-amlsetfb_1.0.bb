SUMMARY = "SysV init scripts for Amlogic framebuffer set-up"
DESCRIPTION = "Provides basic set-up for the amlogic framebuffer"
SECTION = "base"
include conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "k1pro|k2pro|k2prov2|k3pro|k1plus|kvim2|c300|c300pro|c400plus|alien4"

PR = "r7"

inherit pkgconfig update-rc.d

INITSCRIPT_NAME = "amlsetfb"
INITSCRIPT_PARAMS = "start 03 S ."

INHIBIT_DEFAULT_DEPS = "1"
RDEPENDS_${PN} = "initscripts fbset fbset-modes"

S = "${WORKDIR}"

SRC_URI = " \
    file://amlsetfb \
    file://aaa \
"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/amlsetfb  ${D}${sysconfdir}/init.d/amlsetfb
    install -m 0755 ${WORKDIR}/aaa  ${D}${sysconfdir}/init.d/aaa
}
