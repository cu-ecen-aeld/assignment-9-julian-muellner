# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

inherit module
inherit update-rc.d
INITSCRIPT_PACKAGES += "${PN}"
INITSCRIPT_NAME:${PN} = "scull-start-stop.sh"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-julian-muellner;protocol=https;branch=master \
           file://0001-added-Makefile.patch \
           file://files/scull-start-stop.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "20693e289dcbd3a83882163c65c3a622128cba0b"

S = "${WORKDIR}/git"

FILES:${PN} += "${sysconfdir}/init.d/scull-start-stop.sh"

do_install () {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 "${WORKDIR}/files/scull-start-stop.sh" ${D}${sysconfdir}/init.d/

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0755 ${S}/scull/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
}

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
