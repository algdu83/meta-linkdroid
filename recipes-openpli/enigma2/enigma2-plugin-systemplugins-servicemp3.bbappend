FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append += "${@bb.utils.contains_any("MACHINE_FEATURES", "linkdroid-pli mecool-pli", "file://servicemp3-alien5.patch", "", d)}"
