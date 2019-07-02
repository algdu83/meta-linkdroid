DESCRIPTION = "Amlogic kernel (CoreELEC tree)"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel machine_kernel_pr
DEPENDS = "xz-native bc-native u-boot-mkimage-native virtual/${TARGET_PREFIX}gcc"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

COMPATIBLE_MACHINE = "k1pro|k2pro|k2prov2|k3pro|k1plus|kvim2|c300|c300pro|c400plus|alien4"

DEPENDS_append_aarch64 = " libgcc"
KERNEL_CC_append_aarch64 = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD_append_aarch64 = " ${TOOLCHAIN_OPTIONS}"

S = "${WORKDIR}/linux-amlogic-coreelec-amlogic-3.14-nougat"
B = "${WORKDIR}/build"

DTS = "${@ d.getVar('KERNEL_DEVICETREE').replace('.dtb','.dts') }"

SRC_URI = "https://github.com/OpenVisionE2/linux-amlogic-coreelec/archive/amlogic-3.14-nougat.tar.gz \
  file://defconfig \
  file://openvision.patch \
  file://${DTS} \
"

SRC_URI[md5sum] = "bd6493603402fd6052b809831e0ab9a4"
SRC_URI[sha256sum] = "c07e1b89fdcd33a41c1e7c089221b9a2e1b09e4a10e8bb22aa94dda2406a4ade"

do_configure_prepend(){
    sed -i "s/@DISTRONAME@/${MACHINE}/" "${WORKDIR}/defconfig"
}

do_compile_append() {
    install -m 0644 ${WORKDIR}/${DTS} ${S}/arch/arm64/boot/dts/amlogic/
    if test -n "${KERNEL_DEVICETREE}"; then
    	for DTB in ${KERNEL_DEVICETREE}; do
    		if echo ${DTB} | grep -q '/dts/'; then
    			bbwarn "${DTB} contains the full path to the the dts file, but only the dtb name should be used."
    			DTB=`basename ${DTB} | sed 's,\.dts$,.dtb,g'`
    		fi
    		oe_runmake ${DTB}
    	done
    	# Create directory, this is needed for out of tree builds
    	mkdir -p ${B}/arch/arm64/boot/dts/amlogic/
    	cp ${B}/arch/arm64/boot/dts/amlogic/${KERNEL_DEVICETREE} ${B}/arch/arm64/boot/
    fi
}
