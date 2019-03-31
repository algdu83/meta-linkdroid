SUMMARY = "Amlogic audio decoders library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "alien5|k1pro|k2pro|k2prov2|k3pro|k1plus|kvim2|c300|c300pro|c400plus|alien4"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCDATE = "20190223"
PR = "${SRCDATE}"

DEPENDS = "linkdroid-libamavutils-alien5 alsa-lib rtmpdump "
RDEPENDS_${PN} = "ffmpeg linkdroid-libamavutils-alien5"

inherit lib_package

SRC_URI[md5sum] = "4a01acdd4a0b5f80f209a38ab4acee40"
SRC_URI[sha256sum] = "8f68f1bbccf72350d6e829ed8cf2e6a2d8c29077df7ab245f51631106f618fd1"

SRC_URI = "http://source.mynonpublic.com/linkdroid/${BPN}-${SRCDATE}.zip"

S = "${WORKDIR}/"

do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${includedir}/amlogic/amadec
    install -d ${D}${includedir}/amlogic/amadec/amports
    install -d ${D}${includedir}/amlogic/amadec/system
    install -d ${D}${libdir}
    install -m 0755 ${S}/include/*.h ${D}${includedir}/amlogic/amadec/
    install -m 0755 ${WORKDIR}/libamadec.so ${D}/${libdir}/
    install -m 0644 ${WORKDIR}/libamadec.pc ${D}${libdir}/pkgconfig/
}

FILES_${PN} = "${libdir}/* "
FILES_${PN}-dev = "${includedir}/*"

do_configure() {
}

do_compile() {
}

do_package_qa() {
}

INSANE_SKIP_${PN} = "already-stripped dev-so ldflags dev-deps textrel"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
